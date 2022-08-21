package com.houhong.springResource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 00:41
 **/
@ComponentScan(basePackageClasses = ScanClass1.class,
useDefaultFilters = false,
includeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM,
        classes = CustomFilter.class)
})
@Configuration
public class ScanClass1 {

}