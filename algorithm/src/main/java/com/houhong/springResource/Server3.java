package com.houhong.springResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 16:26
 **/
@Component
public class Server3 {

    @Autowired
    private List<Iservice> iserviceList;

    @PostConstruct
    public  void test(){

        System.out.println(iserviceList.size());
    }
}