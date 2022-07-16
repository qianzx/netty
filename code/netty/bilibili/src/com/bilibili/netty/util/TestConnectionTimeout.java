package com.bilibili.netty.util;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.util
 * @ClassName TestConnectionTimeout
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/16-15:46
 * @Version 1.0
 */
public class TestConnectionTimeout {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture channelFuture = bootstrap.group(group)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler())
                .connect(new InetSocketAddress("localhost", 8080));
        ChannelFuture future = channelFuture.sync();
        future.channel().closeFuture().sync();
    }

}
