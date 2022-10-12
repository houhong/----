package com.houhong.redis;

import redis.clients.jedis.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: algorithm-work
 * @description: 漏桶限流算法
 * @author: houhong
 * @create: 2022-09-23 00:49
 **/
public class FunnelRateLimiter {

    private static class Funnel {

        int capacity;

        float leakingRating;

        int leftQuota;

        long leakingTs;


        public Funnel(int capacity, float leakingRating, int leftQuota, long leakingTs) {
            this.capacity = capacity;
            this.leakingRating = leakingRating;
            this.leftQuota = leftQuota;
            this.leakingTs = leakingTs;
        }

        public Funnel(int capacity, float leakingRating) {

            this.capacity = capacity;
            this.leakingRating = leakingRating;
        }

        public void makeSpace() {

            long nowTs = System.currentTimeMillis();
            long datalaTime = nowTs - leakingTs;

            int datalaData = (int) (leakingRating * datalaTime);

            if (datalaData < 0) {
                this.leftQuota = capacity;
                this.leakingTs = nowTs;
                return;
            }

            if (datalaData < 1) {
                return;
            }

            this.leftQuota += datalaData;
            if (this.leftQuota > this.capacity) {
                this.leftQuota = this.capacity;
            }

        }

        public boolean watering(int quato) {

            makeSpace();

            if (this.leftQuota >= quato) {
                this.leftQuota = quato;
                return true;
            }

            return false;
        }


    }

    public static void main(String[] args) {

        System.out.println(13 & 2);

    }

    private Map<String, Funnel> funnelMap = new ConcurrentHashMap<>(16);


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
    public Boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {

        String key = String.format("hsit:%s,%s", userId, actionKey);

        Funnel funnel = funnelMap.get(key);

        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnelMap.put(key, funnel);
        }
        return funnel.watering(1);
    }


}