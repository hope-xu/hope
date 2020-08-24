package com.interceptor;

import com.annotation.AutoIdempotent;
import com.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: hope
 * @description: 注解拦截器
 * @author: hope
 * @create: 2020-08-23 18:24
 **/

@Component
public class IdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        AutoIdempotent annotation = method.getAnnotation(AutoIdempotent.class);
        //如果注解有属性，则获取进行处理即可
//        String desc = annotation.desc();
        //说明方法有注解
        if (annotation != null) {
            try {
                return tokenService.checkToken(request);
            } catch (Exception e) {
                throw e;
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

