package com.dcits.gaaxy.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dcits.gaaxy.base.bean.AbstractBean;
import com.dcits.gaaxy.base.exception.GalaxyException;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hope
 * Date： 2020/10/28  19:44
 * 描述：
 */
public class BeanUtils {
    private static Map<Class, Map<String, PropertyDescriptor>> classPropertyDescriptors = new ConcurrentHashMap<>();
    public static ExpressionParser parser = new SpelExpressionParser();

    public BeanUtils() {
    }

    public static String getString(Object o) {
        try {
            JSONObject printJson = new JSONObject();
            JSONObject objJson = new JSONObject();
            objJson.put("clazz", o.getClass().getName());
            printJson.put("obj", objJson);
            printJson.put("bean", o);
            return JSON.toJSONString(printJson, new SerializerFeature[]{SerializerFeature.PrettyFormat});
        } catch (Throwable var3) {
            return var3.toString();
        }
    }

    public static Object getFieldValue(Object object, String fieldName) {
        //备注学习
        EvaluationContext context = new StandardEvaluationContext(object);
        return parser.parseExpression(fieldName).getValue(context, Object.class);
    }

    public static void setProperty(Object obect, String fieldName, Object fieldValue) {
        try {
            getPropertyDescriptor(obect, fieldName).getWriteMethod().invoke(obect, fieldValue);
        } catch (Exception var4) {
            throw new GalaxyException(var4);
        }
    }

    public static PropertyDescriptor getPropertyDescriptor(Object object, String fieldName) {
        Map<String, PropertyDescriptor> map = getPropertyDescriptors(object.getClass());
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) map.get(fieldName);
        if (propertyDescriptor == null) {
            throw new GalaxyException(object.getClass().getName() + "类没有" + fieldName + "属性");
        } else {
            return propertyDescriptor;
        }
    }

    private static Map<String, PropertyDescriptor> getPropertyDescriptors(Class clazz) {
        Map<String, PropertyDescriptor> propertyMap = (Map) classPropertyDescriptors.get(clazz);
        if (propertyMap == null) {
            propertyMap = new HashMap<>();
            BeanInfo beanInfo = null;

            try {
                beanInfo = Introspector.getBeanInfo(clazz);
            } catch (IntrospectionException var8) {
                var8.printStackTrace();
            }

            if (beanInfo == null) {
                return null;
            }

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] arr$ = propertyDescriptors;
            int len$ = propertyDescriptors.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                PropertyDescriptor propertyDescriptor = arr$[i$];
                if (propertyDescriptor.getWriteMethod() != null && propertyDescriptor.getReadMethod() != null) {
                    ((Map) propertyMap).put(propertyDescriptor.getName(), propertyDescriptor);
                }
            }
            classPropertyDescriptors.put(clazz, propertyMap);
        }
        return (Map) propertyMap;
    }


    public static Object getProperty(Object oblect, String fieldName) {
        try {
            return getPropertyDescriptor(oblect, fieldName).getReadMethod().invoke(oblect);
        } catch (Exception var3) {
            throw new GalaxyException(var3);
        }

    }

    public static void setFieldValue(Object object, String fieldName, Object fieldValue) {
        if (object != null) {
            if (fieldName.indexOf(".") < 0) {
                setProperty(object, fieldName, fieldValue);
            } else {
                String[] field = splitByFirst(fieldName);
                setFieldValue(getProperty(object, field[0]), field[1], fieldValue);
            }
        }
    }

    private static String[] splitByFirst(String fieldName) {
        return fieldName.split("\\.",2);
    }


}
