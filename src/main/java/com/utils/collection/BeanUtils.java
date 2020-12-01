package com.utils.collection;

import java.util.*;

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


}
