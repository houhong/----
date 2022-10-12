package com.houhong.thirdpay.core;

import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

/**
 * @program: algorithm-work
 * @description: 默认的redis资源实现器
 * @author: houhong
 * @create: 2022-10-06 17:12
 **/
@Component
public class DefaultRedisFactory implements RedisResourceFactory {


    @Override
    public JedisPool getJedisPool() {

        return new JedisPool();
    }
}