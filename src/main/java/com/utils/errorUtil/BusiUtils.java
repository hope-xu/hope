package com.utils.errorUtil;

/**
 * @author Hope
 * Date： 2020/07/13  11:54
 * 描述：
 */
public class BusiUtils {

    public static String getErrorMsg(String errCode) {
        return ErrorUtils.getErrorDesc(errCode, "");
    }

    public static String getErrorMsg(String errCode, String... var1) {
        return ErrorUtils.getParseErrorDesc(errCode, "", var1);
    }


}
