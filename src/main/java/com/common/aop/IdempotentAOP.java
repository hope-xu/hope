package com.common.aop;

import com.common.exception.IdempotentException;
import com.common.token.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: hope
 * @description:
 * @author: hope
 * @create: 2020-08-23 18:53
 * 幂等性AOP实现，需要删除IdempotentInterceptor WebMvcConfig两个类
 **/

@Component
@Aspect
public class IdempotentAOP {

    @Autowired
    TokenService tokenService;

    @Pointcut("@annotation(com.common.annotation.AutoIdempotent)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws IdempotentException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            tokenService.checkToken(request);
        } catch (IdempotentException e) {
            throw e;
        }


    }


}

