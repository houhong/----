package com.houhong.redisframwork.bitmap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * @program: algorithm-work
 * @description: 使用bitMap进行统计
 * @author: houhong
 * @create: 2022-09-26 23:30
 **/
@Service
@Slf4j
public class CheckInService {

    private static final String CHECK_IN_PRE_KEY = "USER_CHECK_IN::DAY::";

    private static final String CONTINUOUS_CHECK_IN_COUNT_PRE_KEY = "USER_CHECK_IN::CONTINUOUS_COUNT::";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");







}