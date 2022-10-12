package com.houhong.redisframwork.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @program: algorithm-work
 * @description: 使用redis完成幂等性校验, 使用aop完成
 *                  // TODO: 2022/9/27
 *                          幂等性完成的几个模式
 *                          1：使用状态机。比如，对于订单支付防止重复支付
 *                                  为每一个唯一性订单编号设计状态：0:未支付，1：支付中，2：支付超时，3：支付成功，3:支付成功
 *                                  为每一位订单设计这样的一个状态存储
 *                          2: 做防重复提交：使用 token 记录。每一次请求在根据请求和具体业务拼接成一个唯一key,然后
 *                          设置一个规定时间d的value的过期时间.value可以为参数。并且为了防止大key，还可以进行md5计算摘要
 *
 *
 * @author: houhong
 * @create: 2022-09-27 00:31
 **/
@Aspect
@Component
public class IdempotenceWithRedis {

    Jedis jedis;

    @PostConstruct
    public void initJedis(){
        jedis = new Jedis("127.0.0.1",6789);

    }
    //日志记录
    private static final Logger logger = LoggerFactory.getLogger(IdempotenceWithRedis.class);

    @Around("@annotation(IdepteValidate)")
    public Object ponintcut(ProceedingJoinPoint pj, IdepteValidate idepteValidate) throws Throwable {

        logger.info("校验重复提交");
        Object[] args = pj.getArgs();

        MethodSignature signature = (MethodSignature) pj.getSignature();

        Method method = signature.getMethod();

        if (!method.isAnnotationPresent(IdepteValidate.class)) {

            return pj.proceed(args);
        }


        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();


        String requestURI = request.getServletPath();
        //获取到url
        StringBuffer realKeyBuider = new StringBuffer();
        String ipAddr = getIpAddr(request);

        //获取用户名
        String oper = request.getHeader("oper");
        if (StringUtils.isEmpty(oper)) {
            oper = idepteValidate.operCode();
        }
        realKeyBuider.append(BaseConstants.ISREAPTED_KEY).append("-")
                .append(oper).append("-")
                .append(ipAddr).append("-")
                .append(requestURI);


        String hasExitedIn30s = (String) redisUtils.get(realKeyBuider.toString());


        String md5Encode = new String();
        String str = new String("");
        String wholeStr = new String();

        BufferedReader br;
        try {
            br = request.getReader();

            while ((str = br.readLine()) != null) {
                wholeStr += str;
            }
            if (StringUtils.isNotEmpty(wholeStr)) {

                //数据进行md5  计算摘要
                md5Encode = Md5Utils.MD5Encode(wholeStr, "utf-8", true);
                //params = JSON.parseObject(wholeStr, Map.class);
            }
        } catch (IOException e1) {
            logger.error("" + e1);
        }
        logger.info("#传入的参数:{}#", wholeStr);

        //获取token -- 第一次获取
        if (StringUtils.isEmpty(hasExitedIn30s)) {
            //放行
            //30秒内不准重复提交 -- s
            //设置过期时间：默认为30s
            int expireSecond = formRepeatSubmitValidation.expireSecond();
            redisUtils.set(realKeyBuider.toString(), md5Encode, expireSecond, TimeUnit.SECONDS);
            return pj.proceed(args);
        } else {


            logger.info("#redis中的参数:{}#", hasExitedIn30s);

            Result<String> failed = null;

            if (StringUtils.isEmpty(hasExitedIn30s) && StringUtils.isEmpty(md5Encode)) {

                failed = ResultUtils.resultWithEnum(BusinessCodeEnum.COMMON_IS_REAPTED_REQUESTED);
            } else {

                if (md5Encode.equals(hasExitedIn30s)) {

                    failed = ResultUtils.resultWithEnum(BusinessCodeEnum.COMMON_IS_REAPTED_REQUESTED);
                } else {
                    return pj.proceed(args);
                }
            }
            return failed;

        }

    }

    public static String getIpAddr(HttpServletRequest request) {
        try {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();

                    // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
                    if (ip != null && ip.length() > 15) { // "***.***.***.***".length()
                        // = 15
                        if (ip.indexOf(",") > 0) {
                            ip = ip.substring(0, ip.indexOf(","));
                        }
                    }
                }
            }
            if (ip != null && ip.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ip.indexOf(",") > 0) {
                    ip = ip.substring(0, ip.indexOf(","));
                }
            }
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}