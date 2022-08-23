package com.houhong.springResource;

import org.springframework.context.annotation.Import;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 01:04
 **/
@Import({
        Server1.class,
        Server2.class
})
public class MainConfig1 {
}