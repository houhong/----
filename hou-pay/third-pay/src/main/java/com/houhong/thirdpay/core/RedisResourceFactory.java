package com.houhong.thirdpay.core;

import redis.clients.jedis.JedisPool;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-10-06 16:38
 **/
public interface RedisResourceFactory {

    /**
     * 获取redis连接池
     *
     * @return
     */
    JedisPool getJedisPool();
}