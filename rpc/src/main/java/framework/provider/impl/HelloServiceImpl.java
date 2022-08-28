package framework.provider.impl;


import framework.provider.api.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "执行服务实现类" + name;
    }
}
