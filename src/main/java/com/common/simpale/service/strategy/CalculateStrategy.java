package com.common.simpale.service.strategy;

import com.google.common.collect.Maps;

import java.util.Map;

public class CalculateStrategy {

    //private static Map<Integer, CalculateService> map = Maps.newConcurrentMap();
    private static Map<Integer, CalculateService> map = Maps.newHashMap();

    public static void register(Integer type, CalculateService calculateService) {
        map.put(type, calculateService);
    }

    public static CalculateService getCalculateService(Integer type) {
        return map.get(type);
    }

}
