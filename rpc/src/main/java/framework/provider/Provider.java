package framework.provider;



import framework.address.RpcURL;
import framework.protocol.NettyServer;
import framework.provider.api.HelloService;
import framework.provider.impl.HelloServiceImpl;
import framework.registry.LocalRegistry;


import java.net.InetAddress;
import java.net.UnknownHostException;

public class Provider {

    public static void main(String[] args) throws UnknownHostException {

        String interfaceName = HelloService.class.getName();

        RpcURL url = new RpcURL(InetAddress.getLocalHost().getHostAddress(), 8000);
        LocalRegistry.registry(interfaceName, HelloServiceImpl.class);

        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostname(), url.getPort());

        System.out.println(String.format("success, 成功暴露%s服务，地址为%s", interfaceName, url.toString()));
    }
}
