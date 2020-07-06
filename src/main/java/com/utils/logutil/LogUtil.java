package com.utils.logutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Hope
 * Date： 2020/04/16  10:35
 * 描述：
 */
public class LogUtil {
    private static final Logger log = LoggerFactory.getLogger(LogUtil.class);

    private static final int PROCESS_TOTAL_LENGTH = 150;

    public static void getBusiMethodStartLog(Map<String, Object> objects) {
        if (!log.isInfoEnabled()) {
            return;
        }

        String clazz = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String out = clazz + " " + methodName + " is starting";
        StringBuffer sb = new StringBuffer();
        int outLength = out.length();
        int start = (PROCESS_TOTAL_LENGTH - outLength) / 2;
        int end = 0;
        if (outLength % 2 == 0) {
            end = (PROCESS_TOTAL_LENGTH - outLength) / 2;
        } else {
            end = (PROCESS_TOTAL_LENGTH - outLength) / 2 + 1;
        }
        for (int i = 0; i < start; i++) {
            sb.append("*");
        }

        out = sb.toString() + out;
        sb = new StringBuffer();
        for (int i = 0; i < end; i++) {
            sb.append("*");
        }

        out = out + sb.toString();

        log.info(out);

        if (objects != null) {
            for (Map.Entry<String, Object> m : objects.entrySet()) {
                log.info(methodName + ":inMethod parameter[" + m.getKey() + "] is : ");
                log.info(m.getValue() + "");
            }
        }


    }
}
