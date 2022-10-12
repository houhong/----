package com.houhong.controller;

import com.houhong.domain.User;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.*;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-28 16:37
 **/
@Controller
public class TestController {


    @ResponseBody
    @RequestMapping(value = "/test/properties", method = RequestMethod.POST)
    public User upload(@RequestBody User user) {
        System.out.println(user);
        return user;
    }

}