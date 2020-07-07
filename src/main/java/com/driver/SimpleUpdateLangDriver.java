package com.driver;

import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * mybatis 修改单表数据通用生成模板
 * @author lj
 *
 */
public class SimpleUpdateLangDriver extends XMLLanguageDriver implements LanguageDriver{

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<set>");

            List<Field> fieldList = new ArrayList<>() ;
            
            while (parameterType != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
                fieldList.addAll(Arrays.asList(parameterType.getDeclaredFields()));
                parameterType = parameterType.getSuperclass(); //得到父类,然后赋给自己
            }
            
            for (Field field : fieldList) {
                if (!field.isAnnotationPresent(Invisible.class)) {  // 排除被Invisble修饰的变量
                    String tmp = "<if test=\"_field != null\">_column=#{_field},</if>";
//                    String tmp = "_column=#{_field},";
                    sb.append(tmp.replaceAll("_field", field.getName()).replaceAll("_column",
                            CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
                }
            }

            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("</set>");

            script = matcher.replaceAll(sb.toString());
            script = "<script>" + script + "</script>";
        }

        return super.createSqlSource(configuration, script, parameterType);
    }
}