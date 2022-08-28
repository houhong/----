package framework.comsumer;


import framework.provider.api.HelloService;
import framework.proxy.ProxyFactory;

public class Consumer {

    public static void main(String[] args) {

//        Invocation invocation = new Invocation(HelloService.class.getName(), "sayHello", new Class[]{String.class}, new Object[]{"周瑜大都督"});
//        NettyClient nettyClient = new NettyClient();
//        String result = nettyClient.send("localhost", 8080, invocation);
//        System.out.println(result);


        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        for (int i = 0; i < 10; i++) {
            String result = helloService.sayHello("你好");
            System.out.println(result);
        }

    }
}
