package com.siyu.service_blog.aspect;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.siyu.common_static.constant.RedisConstant;
import com.siyu.common_static.constant.ResultCodeConstant;
import com.siyu.common_static.enums.VisitorLoggerEnum;
import com.siyu.common_utils.RedisUtil.RedisUtil;
import com.siyu.service_base.result.Result;
import com.siyu.service_blog.annotation.VisitLogger;
import com.siyu.service_blog.entity.Visitor;
import com.siyu.service_blog.entity.VisitorLog;
import com.siyu.service_blog.entity.vo.BlogDetailVo;
import com.siyu.service_blog.service.VisitorLogService;
import com.siyu.service_blog.service.VisitorService;
import com.siyu.service_blog.util.IpUtil;


@Component
@Aspect
public class VisitLogAspect {
    
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    VisitorService visitorService;

    @Autowired
    VisitorLogService visitorLogService;

    @Pointcut("@annotation(visitLogger)")
	public void logPointcut(VisitLogger visitLogger) {
	}

    @Around("logPointcut(visitLogger)")
	public Object logAround(ProceedingJoinPoint joinPoint, VisitLogger visitLogger) throws Throwable {
		Result result = (Result) joinPoint.proceed();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identifier = getIdentifier(request);
        saveVisitorLog(request ,joinPoint, visitLogger.value(), identifier, result);
        return result;
	}

    private void saveVisitorLog(HttpServletRequest request, ProceedingJoinPoint joinPoint, VisitorLoggerEnum visitorLoggerEnum, String identifier, Result result) {
        String uri = request.getRequestURI();
		String method = request.getMethod();
        String behavior = visitorLoggerEnum.getBehavior();
        String content = visitorLoggerEnum.getContent();

        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> argsMap = new LinkedHashMap<>();
        for(int i = 0; i < args.length; i++) {
            argsMap.put(argNames[i], args[i]);
        }

        String params = JSON.toJSONString(argsMap);
        params = params.length() >= 1000 ? params.substring(0, 1000) : params;
        
        if(result.getCode() == ResultCodeConstant.SUCCESS) {
            switch(visitorLoggerEnum) {                
                case DEFAULT:
                    break;
                case BLOG:
                    BlogDetailVo blog = (BlogDetailVo) result.getData().get("blog");
                    String id = blog.getId();
                    redisUtil.incrementToHashByHashKey(RedisConstant.BLOG_VIEW_HASH_KEY, id, 1);
                    content = blog.getTitle();
                    break;
                case HOME:
                    break;
                case MAP:
                    break;
                case REPOSITORY:
                    break;
                case SEARCH:
                    content = (String) argsMap.get("query");
                    break;
                case TAG:
                    content = (String) argsMap.get("title");
                    break;
                case TYPE:
                    content = (String) argsMap.get("title");
                    break;
                default:
                    break;
            }
        }
        VisitorLog visitorLog = VisitorLog.builder()
                                          .uri(uri)
                                          .method(method)
                                          .uuid(identifier)
                                          .behavior(behavior)
                                          .content(content)
                                          .params(params)
                                          .build();
        visitorLogService.save(visitorLog);
    }

    private String getIdentifier(HttpServletRequest request) {
        String identifier = request.getHeader("identifier");
        if(StringUtils.isEmpty(identifier)) { 
            identifier = issuedIdentifier(request);
        } else {
            if(!redisUtil.isInSet(RedisConstant.IDENTIFIER_SET_KEY, identifier)) {
                boolean visitorInMysql = visitorService.count(new QueryWrapper<Visitor>().eq("uuid", identifier)) > 0;
                if(visitorInMysql) {
                    redisUtil.saveToSet(RedisConstant.IDENTIFIER_SET_KEY, identifier);
                } else { //虚假identifier 重新签发
                    identifier = issuedIdentifier(request);
                }
            }
        }
        return identifier;
    }

    /**
     * 签发UUID
     * @param request
     * @return
     */
    private String issuedIdentifier(HttpServletRequest request) {
        
        Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		String time = Long.toString(calendar.getTimeInMillis() / 1000);

        String ip = IpUtil.getIp(request);
        String userAgent = request.getHeader("User-Agent");
        String nameUUID = ip + time + userAgent;
        String identifierUUID = UUID.nameUUIDFromBytes(nameUUID.getBytes()).toString();


        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addHeader("identifier", identifierUUID);
        response.addHeader("Access-Control-Expose-Headers", "identifier");
        
        visitorService.saveVisitor(identifierUUID, ip, userAgent);
        redisUtil.saveToSet(RedisConstant.IDENTIFIER_SET_KEY, identifierUUID);
        return identifierUUID;
    }
}
