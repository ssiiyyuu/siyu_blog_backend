package com.siyu.service_admin.aspect;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.siyu.common_static.enums.OperatorLoggerEnum;
import com.siyu.service_admin.annotation.OperatorLogger;
import com.siyu.service_admin.entity.OperatorLog;
import com.siyu.service_admin.service.OperatorLogService;
import com.siyu.service_admin.service.UserService;


@Component
@Aspect
public class OperateLogAspect {

    @Autowired
    OperatorLogService operatorLogService;

    @Autowired
    UserService userService;

	ThreadLocal<Long> currentTime = new ThreadLocal<>();

    @Pointcut("@annotation(operatorLogger)")
	public void logPointcut(OperatorLogger operatorLogger) {
	}

    @Around("logPointcut(operatorLogger)")
	public Object logAround(ProceedingJoinPoint joinPoint, OperatorLogger operatorLogger) throws Throwable {
        currentTime.set(System.currentTimeMillis());
        Object object = joinPoint.proceed();
		int times = (int) (System.currentTimeMillis() - currentTime.get());
		currentTime.remove();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        saveOperatorLog(request ,joinPoint, operatorLogger.value(), times);
        return object;
	}

    private void saveOperatorLog(HttpServletRequest request, ProceedingJoinPoint joinPoint, OperatorLoggerEnum operatorLogger, int times) {
        String uri = request.getRequestURI();
		String method = request.getMethod();
        String token = request.getHeader("X-token");
        Map<String, Object> map = userService.info(token);
        String name = (String) map.get("name");
        String params = null;
        String behavior = operatorLogger.getBehavior();

        if(operatorLogger != OperatorLoggerEnum.EXCEL) {
            String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            Object[] args = joinPoint.getArgs();
            Map<String, Object> argsMap = new LinkedHashMap<>();
            for(int i = 0; i < args.length; i++) {
                if(argNames[i].equals("token")) { //参数token不记录
                    continue;
                }
                argsMap.put(argNames[i], args[i]);
            }
            params = JSON.toJSONString(argsMap);
            params = params.length() >= 1000 ? params.substring(0, 1000) : params;
        }

        OperatorLog operatorLog = OperatorLog.builder()
                                             .uri(uri)
                                             .method(method)
                                             .user(name)
                                             .behavior(behavior)
                                             .params(params)
                                             .times(times)
                                             .build();
        operatorLogService.save(operatorLog);
    }


}
