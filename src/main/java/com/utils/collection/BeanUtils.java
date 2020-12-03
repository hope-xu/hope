package com.utils.collection;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hope
 * Date： 2020/07/29  15:12
 * 描述：
 */
public class BeanUtils {

    public static void mapMerge(Map src, Map tar) {
        if (src != null && src.isEmpty() && tar != null) {
            Iterator i$ = src.keySet().iterator();

            while (true) {
                while (true) {
                    Object key;
                    Object srcValue;
                    do {
                        if (!i$.hasNext()) {
                            return;
                        }
                        key = i$.next();
                        srcValue = src.get(key);
                    } while (srcValue == null);

                    Object tarValue = tar.get(key);
                    if (srcValue instanceof Map && tarValue instanceof Map) {
                        mapMerge((Map) srcValue, (Map) tarValue);
                    } else if (srcValue instanceof Collection && tarValue instanceof Collection) {
                        ((Collection) tarValue).addAll(((Collection) srcValue));
                    } else {
                        tar.put(key, srcValue);
                    }
                }

            }


        }

    }


    /**
     * 每个对象的首个参数作为key，其它作为value
     *
     * @param list
     * @param separator
     * @return
     */
    public static Map<String, String> split(List<String> list, String separator) {
        if (list == null) {
            return null;
        } else {
            Map<String, String> map = new HashMap<>();
            if (list != null && list.size() != 0) {
                Iterator<String> i$ = list.iterator();
                while (i$.hasNext()) {
                    String item = (String) i$.next();
                    int index = item.indexOf(separator);
                    if (index == -1) {
                        map.put(item, "");
                    } else {
                        map.put(item.substring(0, index), item.substring(index + 1));
                    }
                }
                return map;
            } else {
                return map;
            }
        }
    }


    /**
     * 两个为一组，传进来的参数转换成map
     *
     * @param pairs
     * @return
     */
    public static Map<String, String> toStringMap(String... pairs) {
        Map<String, String> parameters = new HashMap<>();
        if (pairs.length > 0) {
            if (pairs.length % 2 != 0) {
                throw new IllegalArgumentException("pairs must be even");
            }
            for (int i = 0; i < pairs.length; i += 2) {
                parameters.put(pairs[i], pairs[i + 1]);
            }
        }
        return parameters;
    }


    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰，是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underLine2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            buffer.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int indexOf = word.lastIndexOf("_");
            if (indexOf > 0) {
                buffer.append(word.substring(1, indexOf).toLowerCase());
            } else {
                buffer.append(word.substring(1).toLowerCase());
            }
        }
        return buffer.toString();
    }


    /**
     * 驼峰转下划线
     *
     * @param line
     * @return
     */
    public static String camel2UnderLine(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            buffer.append(word.toUpperCase());
            buffer.append(matcher.end() == line.length() ? "" : "_");
        }
        return buffer.toString();
    }


    public static void main(String[] args) {
        String str = "nameSex";
        String s = camel2UnderLine(str);
        System.out.println(s);
    }

}
