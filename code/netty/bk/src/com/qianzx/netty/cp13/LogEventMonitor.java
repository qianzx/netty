package com.qianzx.netty.cp13;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-20 17:42:00
 */
public class LogEventMonitor {
    public static Integer SPORT = 1112;
    public static Integer DPORT = 1111;
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public LogEventMonitor() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LogEventDecoder());
                        pipeline.addLast(new LogEventHandler());
                    }
                });

    }
      public static void main(String[] args) throws Exception {
          LogEventMonitor logEventMonitor = new LogEventMonitor();
          try {
              Channel channel = logEventMonitor.bind();
              System.out.println("LogEventMonitor running");
              channel.closeFuture().sync();
          }finally {
              logEventMonitor.stop();
          }
      }

    private void stop() {
        group.shutdownGracefully();
    }

    private Channel bind() {
        return bootstrap.bind(SPORT).syncUninterruptibly().channel();
    }
}

