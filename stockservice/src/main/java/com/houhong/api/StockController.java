package com.houhong.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-01 13:14
 **/
@Controller
public class StockController {


    @ResponseBody
    @PostMapping("/byte")
    public byte[] testByte(@RequestBody Byte[] byteAr) throws Exception {

        String hello = new String("hello");
        return hello.getBytes("utf-8");
    }
}