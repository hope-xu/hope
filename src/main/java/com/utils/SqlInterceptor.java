package com.utils;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.Properties;

/**
 * @author Hope
 * Date： 2021/05/17  10:31
 * 描述：自定义sql拦截器
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class, ResultHandler.class})})
public class SqlInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(SqlInterceptor.class);


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try{
            return invocation.proceed();
        }finally {
            long endTime = System.currentTimeMillis();
            if (log.isInfoEnabled()) {
                log.info("sql excute time:{ "+(endTime-startTime)+"ms }");
            }
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
