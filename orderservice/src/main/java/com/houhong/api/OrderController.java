package com.houhong.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-01 13:20
 **/
@RestController
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/order")
    private  String  order(String msg){



        return  null;
    }

}