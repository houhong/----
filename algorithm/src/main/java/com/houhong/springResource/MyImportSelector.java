package com.houhong.springResource;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 01:20
 **/
public class MyImportSelector implements ImportSelector {


    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();

        return new String[]{
                "com.houhong.springResource.Server1",
                "com.houhong.springResource.Server2"
        };
    }
}