package com.houhong.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @program: algorithm-work
 * @description: 基于redis的限流
 * @author: houhong
 * @create: 2022-09-23 00:21
 **/
public class SimpleRateLimiter {

    private Jedis jedis;

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * @param actionKey 行为
     * @param period    时间间隔
     * @param maxCount  最大次数
     * @param userId    用户id
     * @return {@link Boolean}
     * @Author houhong
     * @Description //TODO
     * @Date 12:23 上午 2022/9/23
     **/
    public Boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) {

        String key = String.format("hsit:%s,%s", userId, actionKey);

        long nowTs = System.currentTimeMillis();

        Pipeline pipeline = jedis.pipelined();

        pipeline.multi();
        /**
         *  第一个是Key,第二个是权重因子，第三个是值
         *
         **/
        pipeline.zadd(key, nowTs, nowTs + "");

        //移除位于 区间在 [0 -- nowTs - period * 10000] 的集合
        pipeline.zremrangeByScore(key, 0, nowTs - period * 10000);
        /**
         *  返回数量
         **/
        Response<Long> count = pipeline.zcard(key);
        //在超过 peroid后使其过期
        pipeline.expire(key, period + 1);

        pipeline.exec();
        pipeline.close();
        return count.get() <= maxCount;
    }
}