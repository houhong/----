package com.houhong.springResource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 01:18
 **/
@Import(
     value = {
             MyImportSelector.class
     }
)
@Configuration
public class MainImport5 {
}