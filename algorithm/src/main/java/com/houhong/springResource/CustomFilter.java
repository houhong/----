package com.houhong.springResource;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @program: algorithm-work
 * @description: 自定义扫描器过滤器
 * @author: houhong
 * @create: 2022-08-22 00:32
 **/
public class CustomFilter implements TypeFilter {


    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

        Class<?> aClass = null;
        try {

            aClass = Class.forName(metadataReader.getClassMetadata().getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Iservice.class.isAssignableFrom(aClass);
    }
}