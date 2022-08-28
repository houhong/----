package framework.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: algorithm-work
 * @description: 入站
 * @author: houhong
 * @create: 2022-08-28 18:32
 **/
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandler channelHandler;

    public NettyServerHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("reading this msg");

        Invocation invocation = (Invocation) msg;
        channelHandler.handler(ctx, invocation);
    }
}