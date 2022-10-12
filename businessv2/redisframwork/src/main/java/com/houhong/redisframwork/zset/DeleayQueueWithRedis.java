package com.houhong.redisframwork.zset;

/**
 * @program: algorithm-work
 * @description:  // TODO: 2022/9/27
 *                      使用redis完成延迟队列
 *                       使用zset，做一个有序集合
 *                        将进入集合的时间+延时的时间 == score，然后每次取zset的zrevrank== 0的值
 *                        如果当前时间 >= socre 表示改消息已经到期，应该消费，否则就不消费。
 *                        缺点：1： 不能做广播？2:消费端的自旋造成的redis server 的cpu压力 休眠
 *
 * @author: houhong
 * @create: 2022-09-27 01:05
 **/
public class DeleayQueueWithRedis {
}