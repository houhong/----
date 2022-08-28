package framework.protocol;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @program: algorithm-work
 * @description: 分发器
 * @author: houhong
 * @create: 2022-08-28 18:37
 **/
@Slf4j
public class DispatcherHandler implements ChannelHandler {


    private ChannelHandler channelHandler;

    private ExecutorService executorService;

    public DispatcherHandler(ChannelHandler channelHandler) {

        log.info("init the DispatcherHandler... default  threadPool");

        executorService = Executors.newFixedThreadPool(10, r -> {

            Thread t = new Thread(r);
            t.setName("【rp-thread-pool】");
            return t;
        });
        this.channelHandler = channelHandler;

    }

    @Override
    public void handler(ChannelHandlerContext ctx, Invocation invocation) throws Exception {

        log.info("execute thread pool");
        executorService.execute(new RpcTask(invocation, channelHandler, ctx));
    }
}