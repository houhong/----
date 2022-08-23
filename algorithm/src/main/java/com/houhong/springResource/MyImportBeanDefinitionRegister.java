package com.houhong.springResource;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 01:14
 **/
public class MyImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        BeanDefinition server1Def = BeanDefinitionBuilder.genericBeanDefinition(Server1.class).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("server1",server1Def);

        BeanDefinition server1De2 = BeanDefinitionBuilder.genericBeanDefinition(Server2.class)
                .addPropertyReference("server1","server1").getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("server2",server1De2);


    }
}