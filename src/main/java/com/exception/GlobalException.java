package com.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: hope
 * @description:
 * @author: hope
 * @create: 2020-08-23 18:35
 **/

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(IdempotentException.class)
    public String idempotenException(IdempotentException e){
       //可自定义返回一个错误对象
        return e.getMessage();
    }



}

