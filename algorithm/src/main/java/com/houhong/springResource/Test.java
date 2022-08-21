package com.houhong.springResource;

import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.AnnotatedElement;
import java.util.Set;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-21 23:49
 **/
@B(value = "houhong",valueA = "houhong")
public class Test {


    public static void main(String[] args) {

        B mergedAnnotation = AnnotatedElementUtils.getMergedAnnotation(Test.class, B.class);

        System.out.println(mergedAnnotation);
        A annotationType2 = AnnotatedElementUtils.getMergedAnnotation(Test.class, A.class);

        System.out.println(annotationType2);
    }
}