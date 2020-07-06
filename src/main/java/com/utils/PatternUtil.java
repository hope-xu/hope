package com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author Hope
 * Date： 2020/04/16  10:35
 * 描述：正则、字符串工具类
 */
public class PatternUtil {

    private static final Logger log = LoggerFactory.getLogger(PatternUtil.class);

    /**
     * 判断字符串是否为汉字
     * @param val 字符串参数
     * @return
     */
    public static boolean ifCh(String val) {
        if (val == null || "".equals(val)) {
            return false;
        }
        Pattern p = compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(val);
        if (m.find()) {
            return true;
        }
        return false;
    }


    public static boolean strIn(String val,String... in){
        for (String value : in) {
            if (value.equals(val))
                return true;
        }
        return false;
    }


}
