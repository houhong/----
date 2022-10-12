package com.houhong.thirdpay.config;

import redis.clients.jedis.JedisPool;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-10-06 17:03
 **/
public class CommonConfig {

    /**
     * 统一编码
     */
    public static final String UNIFY_CHARSET = "UTF-8";

    /**
     * redis连接池
     */
    private static JedisPool JEDIS_POOL;

    public static void setJedisPool(JedisPool jedisPool){
        JEDIS_POOL = jedisPool;
    }

    public static JedisPool getJedisPool(){
        return JEDIS_POOL;
    }
}