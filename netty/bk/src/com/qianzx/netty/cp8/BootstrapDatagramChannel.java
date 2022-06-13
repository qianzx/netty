package com.qianzx.netty.cp8;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import org.ietf.jgss.Oid;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-13 15:33:00
 */
public class BootstrapDatagramChannel {
    public void bootstrap(){
        Bootstrap bootstrap = new Bootstrap();
        final OioEventLoopGroup group = new OioEventLoopGroup();
        bootstrap.group(group)
                .channel(OioServerSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {

                    }
                });
        ChannelFuture future = bootstrap.bind("127.0.0.1", 9999);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
            if(future.isSuccess()){
                System.out.println("CHannel bound");
            }else {
                System.out.println("Bind attempt failed");
                future.cause().printStackTrace();
                group.shutdownGracefully();
            }
            }
        });
    }
}

