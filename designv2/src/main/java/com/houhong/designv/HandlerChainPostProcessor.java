package com.houhong.designv;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @program: algorithm-work
 * @description: 责任链后置处理器
 * @author: houhong
 * @create: 2022-08-26 00:50
 **/
@Component
public class HandlerChainPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;


    private Map<Integer, AbstractHandler> handlerMap = new ConcurrentHashMap<>(16);

    /**
     * `
     * 在Bean 的后置处理器里面去
     **/
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {


        DutyChain dutyChain = bean.getClass().getAnnotation(DutyChain.class);

        //找到所有的Bean
        if (bean instanceof AbstractHandler && dutyChain != null) {

            int order = dutyChain.order();
            AbstractHandler abstractHandler = (AbstractHandler) bean;
            handlerMap.put(order, abstractHandler);

            //然后进行排序
            Set<Integer> keySet = handlerMap.keySet();
            TreeSet<Integer> collect = keySet.stream().collect(Collectors.toCollection(TreeSet::new));
            for (Integer integer : collect) {
                AbstractHandler cur = handlerMap.get(integer);
                if (cur.getNext() == null) {
                    for (Integer next : collect) {
                        if (integer < next) {
                            AbstractHandler nextHandler = handlerMap.get(next);
                            cur.setNext(nextHandler);
                            break;
                        }
                    }
                }
            }

            System.out.println(applicationContext.getBean(FirstHandler.class).getNext());
        }

        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        Map<Integer, AbstractHandler> handlerMap = new ConcurrentHashMap<>(16);


        AbstractHandler fi = new FirstHandler();

        AbstractHandler f2 = new SecondHandler();
        handlerMap.put(1, fi);
        handlerMap.put(2, f2);

        handlerMap.keySet().forEach(order -> {
            if (order == 1) {
                AbstractHandler abstractHandler = handlerMap.get(order);
                AbstractHandler next = handlerMap.get(2);
                abstractHandler.setNext(next);
            }
        });

        System.out.println(fi.getNext());
    }

}