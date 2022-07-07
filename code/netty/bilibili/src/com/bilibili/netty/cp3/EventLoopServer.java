package com.bilibili.netty.cp3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp3
 * @ClassName EventServer
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/3-19:29
 * @Version 1.0
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        //第一个参数ServerSockerChannel服务，第二个参数为ServerSockerChannel的accept方法产生的socketChannel服务
        //EventLoopGroup默认线程数为cpu数的2倍

        //DefaultEventLoopGroup只能处理普通任务和定时任务，不能处理IO事件
        DefaultEventLoopGroup eventExecutors = new DefaultEventLoopGroup();
        bootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                //调整系统的接收缓冲区（滑动窗口）
                .option(ChannelOption.SO_RCVBUF,10)
                //调整SocketChannel接收缓冲区
                .childOption(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(16,16,16))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println(byteBuf.toString(Charset.defaultCharset()));
                                System.out.println(Thread.currentThread().getName());
                                //log.debug(byteBuf.toString(Charset.defaultCharset()));
                                ctx.fireChannelRead(msg);


                                //不要阻塞当前I/O线程（EventLoop绑定的线程）。
                                // 永远不要将一个长时间运行的任务放入到执行队列中，
                                // 因为它将阻塞需要在同一线程上执行的任何其他任务。
                                // 如果必须进行阻塞调用或者执行长时间运行任务，使用EventExecutor。
                            }
                        }).addLast(eventExecutors,"handler2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println(byteBuf.toString(Charset.defaultCharset()));
                                System.out.println(Thread.currentThread().getName());
                            }
                        });
                    }
                })
                .bind(8080);

    }
}
