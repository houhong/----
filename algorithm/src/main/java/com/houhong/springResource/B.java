package com.houhong.springResource;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@A(value = "1234")
public @interface B {

    String value() default  "B";

    @AliasFor(annotation = A.class,value = "value")
    String valueA() default "12456";
}
