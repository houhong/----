package com.houhong.redisframwork.bitmap;

/**
 * @program: algorithm-work
 * @description: // TODO: 2022/9/27
 *                       对于 char[] == 1byte == 8bit 可以做8位的二值统计 这样对于10byte = 80bit 1024byte == 1024 * 8的统计
 *                       最后以char 存储
 *                       举列 ： 用户一年的打卡记录： key = usid:sign:userId:yyyyMM  offset 0/1 (offset == 日)
 *                       SETBIT usid:sign:109452:202209 15 1 ==  工号为109452的员工在202209 15号 打卡
 *                       GETBIT usid:sign:109452:202209 15  ==  工号为109452的员工在202209 15号 是否打卡
 * @author: houhong
 * @create: 2022-09-27 01:16
 **/
public class UserCount {
}