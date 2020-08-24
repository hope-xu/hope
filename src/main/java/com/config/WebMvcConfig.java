package com.config;

import com.interceptor.IdempotentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: hope
 * @description:
 * @author: hope
 * @create: 2020-08-23 18:34
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    IdempotentInterceptor idempotentInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idempotentInterceptor).addPathPatterns("/**");
    }
}

