package com.common.token;

import com.common.exception.IdempotentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @program: hope
 * @description: TokenService 生成令牌 检查令牌
 * @author: hope
 * @create: 2020-08-23 18:08
 **/

@Component
public class TokenService {

    @Autowired
    RedisService redisService;

    /**
     * 返回可以是一个对象
     *
     * @return
     */
    public String createToken() {
        String uuid = UUID.randomUUID().toString();
        redisService.setEx(uuid,uuid,10000L);
        return uuid;
    }

    /**
     * 检查token
     * 如果请求头没有，则从参数中获取
     *
     * @param request
     * @return
     */
    public boolean checkToken(HttpServletRequest request) throws IdempotentException {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
            //还没有，则抛异常
            if (StringUtils.isEmpty(token)) {
                throw new IdempotentException("token 不存在");
            }
        }
        //redis是否有token
        if (!redisService.exists(token)) {
            throw new IdempotentException("重复的操作");
        }
        boolean remove = redisService.remove(token);
        if (!remove) {
            throw new IdempotentException("重复的操作");
        }
        return true;
    }


}

