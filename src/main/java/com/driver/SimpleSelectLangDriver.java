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
 * mybatis 查询单表数据通用生成模板
 * @author lj
 *
 */
public class SimpleSelectLangDriver extends XMLLanguageDriver implements LanguageDriver {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {

        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<where>");

            List<Field> fieldList = new ArrayList<>() ;
            
            while (parameterType != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
                fieldList.addAll(Arrays.asList(parameterType.getDeclaredFields()));
                parameterType = parameterType.getSuperclass(); //得到父类,然后赋给自己
            }
            
            for (Field field : fieldList) {
            	if (!field.isAnnotationPresent(Invisible.class) || field.isAnnotationPresent(BeginEnd.class)) {  // 排除被Invisble修饰的变量
            		String tmp = "";
            		if(field.isAnnotationPresent(Like.class)){
            			tmp = "<if test=\"_field != null and _field != '' \"> AND _column like concat(concat('%',#{_field}),'%')</if>";
            		}else if(field.isAnnotationPresent(BeginEnd.class)){
            				tmp = "<if test=\"beginDate != null and beginDate != '' \"> AND _column &gt;= #{beginDate} </if>" +
            		
            				 "<if test=\"endDate != null and endDate != '' \"> AND _column &lt;= #{endDate} </if>";
            		}else{
            			Class<?> type = field.getType();
            			if ("java.util.Date".equals(type.getName())) {
            				tmp = "<if test=\"_field != null  \"> AND _column=#{_field}</if>";
						}else {
							tmp = "<if test=\"_field != null and _field != '' \"> AND _column=#{_field}</if>";
						}
            		}
	                sb.append(tmp.replaceAll("_field", field.getName()).replaceAll("_column",
	                        CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
            	}
            }

            sb.append("</where>");

            script = matcher.replaceAll(sb.toString());
            script = "<script>" + script + "</script>";
        }

        return super.createSqlSource(configuration, script, parameterType);
    }
}
