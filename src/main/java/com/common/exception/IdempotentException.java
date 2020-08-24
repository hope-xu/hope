package com.common.exception;

/**
 * @program: hope
 * @description:
 * @author: hope
 * @create: 2020-08-23 18:15
 **/

public class IdempotentException extends Exception {

    public IdempotentException(String msg) {
    super(msg);
    }

}

