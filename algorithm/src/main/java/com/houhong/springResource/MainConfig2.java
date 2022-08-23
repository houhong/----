package com.houhong.springResource;

import org.springframework.context.annotation.Import;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 01:04
 **/
@Import({
        MouduleConfig.class,
        MouduleConfig2.class
})
public class MainConfig2 {
}