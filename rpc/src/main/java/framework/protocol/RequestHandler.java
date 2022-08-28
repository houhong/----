package framework.protocol;

import framework.registry.LocalRegistry;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @program: algorithm-work
 * @description: 服务端执行
 * @author: houhong
 * @create: 2022-08-28 18:43
 **/
@Slf4j
public class RequestHandler  implements  ChannelHandler{


    @Override
    public void handler(ChannelHandlerContext ctx, Invocation invocation) throws Exception {


        log.info("execute provider.... ");


        Class serviceImpl = LocalRegistry.getClass(invocation.getInterfaceClass());

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParamsTypes());
        Object result = method.invoke(serviceImpl.newInstance(), invocation.getParams());


        log.info("execute success,return result:{} ",result);
        ctx.writeAndFlush("Netty:" + result);
    }
}