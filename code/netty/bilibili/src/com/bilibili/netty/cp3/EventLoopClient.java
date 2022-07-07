package com.bilibili.netty.cp3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp3
 * @ClassName EventLoopClient
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/3-19:36
 * @Version 1.0
 */
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture channelFuture = bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder(Charset.defaultCharset()));
                    }
                })
                //connect 是异步的，main发起的调用，真正执行的connect是NioEventLoop绑定的线程
                .connect(new InetSocketAddress("localhost", 8080));
                //如果不阻塞获取的channel没建立好连接
           /*   //1:使用sync()同步处理
            ChannelFuture future = channelFuture.sync();//阻塞当前线直到EventLoop线程连接建立完毕
            Channel channel = future.channel();
            System.out.println(channel);
            System.out.println("");*/
        //2:使用addListener()异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                System.out.println(channel);
                System.out.println("");
            }
        });


    }
}
