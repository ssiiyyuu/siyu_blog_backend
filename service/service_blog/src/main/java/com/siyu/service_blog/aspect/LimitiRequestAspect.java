package com.siyu.service_blog.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.siyu.common_static.enums.ExceptionEnum;
import com.siyu.common_utils.RedisUtil.RedisUtil;
import com.siyu.service_base.exceptionHandler.GlobalException;
import com.siyu.service_blog.annotation.LimitRequest;

@Component
@Aspect
public class LimitiRequestAspect {

    @Autowired
    RedisUtil redisUtil;

    @Pointcut("@annotation(limitRequest)")
	public void limitPointcut(LimitRequest limitRequest) {
	}
    
    @Before("limitPointcut(limitRequest)")
    public void doBefore(JoinPoint joinPoint, LimitRequest limitRequest) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uuid = request.getHeader("identifier");
		String method = request.getMethod();
        String uri = request.getRequestURI();
        String key = uuid + ":" + uri + method;
        Integer count = (Integer) redisUtil.getFromValue(key);
    
        int max = limitRequest.counts();
        int seconds = limitRequest.seconds();

        if(count == null) {
            redisUtil.incrementToValue(key, 1);
            redisUtil.expire(key, seconds);
        } else {
            if(count >= max) {
                throw new GlobalException(
                    ExceptionEnum.TOO_MANY_REQUEST, max + " request are allowed every " + seconds + " seconds"
                );
            } else {
                redisUtil.incrementToValue(key, 1);
            }
        }
    }
}
