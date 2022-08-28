package framework.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * @program: algorithm-work
 * @description: netty服务
 * @author: houhong
 * @create: 2022-08-28 18:34
 **/
public class NettyServer {

    /**
    *  io线程
    **/
    public static Integer iothreads = 10;

    public void start(String hostname, Integer port) {
        try {
            final ServerBootstrap bootstrap = new ServerBootstrap();

            EventLoopGroup bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("NettyServerBoss", true));
            EventLoopGroup workerGroup = new NioEventLoopGroup(iothreads, new DefaultThreadFactory("NettyServerWorker", true));

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            /**
                            *  解码器
                            **/
                            pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers
                                    .weakCachingConcurrentResolver(this.getClass()
                                            .getClassLoader())));
                            /**
                            *  编码器
                            **/
                            pipeline.addLast("encoder", new ObjectEncoder());
                            pipeline.addLast("handler", new NettyServerHandler(new DispatcherHandler(new RequestHandler())));
                        }
                    });
            bootstrap.bind(hostname, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}