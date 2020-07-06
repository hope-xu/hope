package com.utils.logutil;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author Hope
 * Date： 2020/07/03  15:29
 * 描述：
 * AOP中的概念
 * Aspect(切面)：指横切性关注点的抽象即为切面，它与类相似，只是两者的关注点不一样，类是对物体特征的抽象，而切面是横切性关注点的抽象。
 * joinpoint(连接点)：所谓连接点是指那些被拦截到的点（可以是方法、属性、或者类的初始化时机(可以是Action层、Service层、dao层)）。在Spring中，这些点指的是方法，因为Spring只支持方法类型的连接点，实际上joinpoint还可以是field或类构造器。
 * Pointcut(切入点)：所谓切入点是指我们要对那些joinpoint进行拦截的定义，也即joinpoint的集合。
 * Advice(通知)：所谓通知是指拦截到joinpoint之后所要做的事情就是通知。通知分为前置通知、后置通知、异常通知、最终通知、环绕通知。我们就以CGlibProxyFactory类的代码为例进行说明：
 */
@Component
@Aspect
public class EagleEyeAOP {

    private static final Logger log = LoggerFactory.getLogger(EagleEyeAOP.class);

    /**
     * 切到所有EagleEye修饰的方法
     */
    @Pointcut("@annotation(com.utils.logutil.EagleEye)")
    public void eagleEye(){
        System.out.println("qiedian");
    }

    @Around("eagleEye()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        //开始时间
        long begin = System.currentTimeMillis();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        EagleEye eagleEye = method.getAnnotation(EagleEye.class);

        //接口描述信息
        String desc = eagleEye.desc();

        log.info("=============请求开始==============");
        log.info("请求链接：{}",request.getRequestURL().toString());
        log.info("接口描述：{}",desc);
        log.info("请求类型：{}",request.getMethod());
        log.info("请求方法：{}",signature.getDeclaringTypeName(),signature.getName());
        log.info("请求IP：{}",request.getRemoteAddr());
        log.info("请求入参：{}", JSON.toJSONString(point.getArgs()));

        Object result = point.proceed();

        long end = System.currentTimeMillis();
        log.info("请求耗时：{} ms",end-begin);
        log.info("请求返回：{}",JSON.toJSONString(result));
        log.info("=============请求结束==============");
        return result;
    }





















}
