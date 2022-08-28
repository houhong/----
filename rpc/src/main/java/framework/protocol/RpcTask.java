package framework.protocol;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: algorithm-work
 * @description: 执行rpc任务
 * @author: houhong
 * @create: 2022-08-28 18:41
 **/
@Slf4j
public class RpcTask implements  Runnable{



    private Invocation invocation;
    private ChannelHandler channelHandler;
    private ChannelHandlerContext ctx;

    public RpcTask(Invocation invocation, ChannelHandler channelHandler, ChannelHandlerContext ctx) {
        this.invocation = invocation;
        this.channelHandler = channelHandler;
        this.ctx = ctx;
    }

    @Override
    public void run() {
        try {
            log.info("rpc Server execute...");
            channelHandler.handler(ctx, invocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}