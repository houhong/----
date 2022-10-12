package com.ohouhong.business.design;

import org.junit.platform.commons.util.AnnotationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Component;

import javax.management.DynamicMBean;
import java.io.IOException;
import java.util.List;

/**
 * @program: algorithm-work
 * @description: 责任链后置处理器
 * @author: houhong
 * @create: 2022-08-26 00:50
 **/
@Component
public class HandlerChainPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Autowired
    private List<AbstractHandler> handlerList;

    /**
     * `
     * 在Bean 的后置处理器里面去
     **/
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        SimpleMetadataReaderFactory factory = new SimpleMetadataReaderFactory();
        MetadataReader metadataReader = null;

        DutyChain dutyChain = bean.getClass().getAnnotation(DutyChain.class);
        //存在
        if (bean instanceof AbstractHandler && dutyChain != null ) {

            int order = dutyChain.order();

        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}