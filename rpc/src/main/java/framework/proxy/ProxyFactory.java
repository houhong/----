package framework.proxy;

import framework.address.RpcURL;
import framework.protocol.Invocation;
import framework.protocol.NettyClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;

/**
 * @program: algorithm-work
 * @description: 代理工厂
 * @author: houhong
 * @create: 2022-08-28 18:53
 **/
@Slf4j
public class ProxyFactory<T>  {

    private Object target;

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(final Class interfaceClass) {

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, (proxy, method, args) -> {

            Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), method.getParameterTypes(), args);

            try {
                NettyClient nettyClient = new NettyClient();

                RpcURL rpcURL = new RpcURL("127.0.0.1",8000);
                String result = nettyClient.send(rpcURL.getHostname(), rpcURL.getPort(), invocation);
                return result;
            } catch (Exception e) {
                System.out.println(e);
                log.error("error ,msg:{}",e);
               return doMock(invocation);
            }
        });
    }

    private static Object doMock(Invocation invocation) {
        return "容错逻辑";
    }
}