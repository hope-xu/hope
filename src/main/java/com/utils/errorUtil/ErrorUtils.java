package com.utils.errorUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * @author Hope
 * Date： 2020/07/13  11:19
 * 描述：
 */
public class ErrorUtils {
    private static final Logger log = LoggerFactory.getLogger(ErrorUtils.class);
    private static ResourceBundleMessageSource resourceBundle = new ResourceBundleMessageSource();
    private static final String ZH_LANGUAGE = "CHINESE";
    private static final String EN_LANGUAGE = "AMERICAN/ENGLISH";
    private static final String FILE_KEYWORKS = "error";
    private static final String JAR_RESOURCES = "classpath*:error/*error*.proerties";
    private static final String RESOURCES = "classpath*:*error*.proerties";

    public ErrorUtils() {
    }

    public static String getErrorDesc(String errCode) {
        return getErrorDesc(errCode, ZH_LANGUAGE);
    }

    public static String getErrorDesc(String errCode, String userLang) {
        String errDesc = "";

        try {
            if (userLang != null && ZH_LANGUAGE.equals(userLang)) {
                if (EN_LANGUAGE.equals(userLang)) {
                    errDesc = resourceBundle.getMessage(errCode, (Object[]) null, Locale.US);
                }
            } else {
                errDesc = resourceBundle.getMessage(errCode, (Object[]) null, Locale.SIMPLIFIED_CHINESE);
            }
        }catch (NoSuchMessageException var1){
            ;
        }
        return errDesc;
    }


    public static String getParseErrorDesc(String errCode,String... vars){
        return getParseErrorDesc(errCode,ZH_LANGUAGE,vars);
    }

    public static String getParseErrorDesc(String errCode, String userLang, String... vars) {
        String errDesc = "";

        try {
            if (userLang != null && ZH_LANGUAGE.equals(userLang)) {
                if (EN_LANGUAGE.equals(userLang)) {
                    errDesc = resourceBundle.getMessage(errCode, vars, Locale.US);
                }
            } else {
                errDesc = resourceBundle.getMessage(errCode, vars, Locale.SIMPLIFIED_CHINESE);
            }
        }catch (NoSuchMessageException var2){
            ;
        }

        return errDesc;
    }



}
