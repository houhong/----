package framework.test;

import framework.protocol.NettyServer;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-28 19:21
 **/
public class TestMain {

    public static void main(String[] args) {

        NettyServer nettyServer = new NettyServer();

        nettyServer.start("127.0.0.1",8000);
    }
}