package com.qianzx.netty.cp8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-13 14:27:00
 */
public class BootstrapClient {
      public static void main(String[] args){
          BootstrapClient client = new BootstrapClient();
          client.bootstrap();
      }

    private void bootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        final EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("recived data");
                    }
                });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.baidu.com", 9090));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("Connection established");
                }else {
                    System.err.println("Connection attempt failed");
                    future.cause().printStackTrace();
                    group.shutdownGracefully();
                }
            }
        });
    }
}

