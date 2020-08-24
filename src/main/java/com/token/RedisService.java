package com.token;

/**
 * @program: hope
 * @description: redsi服务
 * @author: hope
 * @create: 2020-08-23 17:44
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    public boolean setEx(String key, Object value, Long expireTime) {
        try {
            ValueOperations ops = redisTemplate.opsForValue();
            ops.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 移除key
     * @param key
     * @return
     */
    public boolean remove(String key){
        if (exists(key)) {
            return redisTemplate.delete(key);
        }
        return false;
    }















}

