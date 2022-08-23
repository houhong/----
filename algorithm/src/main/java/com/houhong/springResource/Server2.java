package com.houhong.springResource;

import org.springframework.stereotype.Component;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-08-22 00:34
 **/
@Component("server2")
public class Server2  implements Iservice{

    private Server1 server1;

    public Server1 getServer1() {

        return server1;
    }

    public void setServer1(Server1 server1) {
        this.server1 = server1;
    }

    @Override
    public String toString() {
        return "Server2{" +
                "server1=" + server1 +
                '}';
    }
}