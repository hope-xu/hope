package com.dcits.gaaxy.base.exception;

/**
 * @author Hope
 * Date： 2020/10/28  20:13
 * 描述：
 */
public class GalaxyException extends RuntimeException {
    private static final long serialVersionUID = 6305545804787143982L;
    public GalaxyException(){}

    public GalaxyException(String message){
        super(message);
    }

    public GalaxyException(Throwable cause){
        super(cause);
    }

    public GalaxyException(String message,Throwable cause){
        super(message,cause);
    }

}
