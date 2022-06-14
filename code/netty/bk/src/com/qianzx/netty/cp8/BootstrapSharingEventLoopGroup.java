package com.qianzx.netty.cp8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-13 15:49:00
 */
public class BootstrapSharingEventLoopGroup {
    public void bootstrap(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        final NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture connectFuture;

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        if(connectFuture.isDone()){

                        }
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx)
                            throws Exception {
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.channel(NioSocketChannel.class)
                                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

                                    }
                                });
                        bootstrap.group(ctx.channel().eventLoop());
                        connectFuture = bootstrap.connect("www.baidu.com", 9999);
                    }
                });
        ChannelFuture future = bootstrap.bind(9999);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("Server bound");
                }else{
                    System.out.println("Bind attempt failed");
                    future.cause().printStackTrace();
                    group.shutdownGracefully();
                }
            }
        });
    }
}

