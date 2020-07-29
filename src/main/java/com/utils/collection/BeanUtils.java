package com.utils.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

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


}
