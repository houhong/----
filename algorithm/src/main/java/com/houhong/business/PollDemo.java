package com.houhong.business;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;
import java.util.Date;

/**
 * @program: algorithm-work
 * @description: 轮训demo
 * @author: houhong
 * @create: 2022-08-11 15:12
 **/
@CrossOrigin("*")
@Controller
@RequestMapping("/polling")
public class PollDemo {

    public  static  final  Long TIME_OUT = 30000L;
    // 存放监听某个Id的长轮询集合
    // 线程同步结构
    public static Multimap<String, DeferredResult<String>> watchRequests = Multimaps.synchronizedMultimap(HashMultimap.create());


    @RequestMapping("/index")
    public String sse() {
        return "polling";
    }
    /**
     */
    @GetMapping(path = "watch/{id}")
    @ResponseBody
    public DeferredResult<String> watch(@PathVariable String id) throws Exception {

        /**
        *
        *  DeferredResult<T>
         *       Springboot 的异步请求
         *           1：在控制层是返回 DeferredResult<T> 后
         *             springboot 会快速释放tomcat线程。然后开启Work-thrad 处理业务
         *             在两种情况下回返回信息：1。1： DeferredResult 超时了
         *                                  1。2： DeferredResult 有返回值
         *       基于此处完成长轮训机制：
         *          1：前端发送请求 后端接收到，然后hold主请求，设置30s的超时时间，如果在DeferedResult
         *           （-->>>> 使用了Spring 的事件机制）在超时时间内  进行了数据变更 然后就返回
         *            如果超时过期 （思考：这里为啥不设置超时完全完毕？） 就发送304 重新建立http链接。
         *
         *
         *
         *
        *
        **/
        DeferredResult<String> deferredResult = new DeferredResult<>(TIME_OUT);
        try {
            // 异步请求完成时移除 key，防止内存溢出
            deferredResult.onCompletion(() -> {
                watchRequests.remove(id, deferredResult);
            });
            watchRequests.put(id, deferredResult);
            return deferredResult;
        } catch (AsyncRequestTimeoutException e) {
            DeferredResult<String> stringDeferredResult = new DeferredResult<>();
            stringDeferredResult.setResult("超时了");
            return stringDeferredResult;
        }
    }

    /**
     * 变更数据
     */
    @GetMapping(path = "publish/{id}")
    @ResponseBody
    public String publish(@PathVariable String id) throws Exception {

        if (watchRequests.containsKey(id)) {
            Collection<DeferredResult<String>> deferredResults = watchRequests.get(id);
            for (DeferredResult<String> deferredResult : deferredResults) {
                deferredResult.setResult("我更新了" + DateUtil.formatDate(new Date()));
            }
        }
        return "success";
    }


}