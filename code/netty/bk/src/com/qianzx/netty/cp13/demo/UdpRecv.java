package com.qianzx.netty.cp13.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-21 10:14:00
 */
public class UdpRecv {
    public static Integer SPORT = 1112;
    public static Integer DPORT = 1111;

    public static void main(String[] args) {

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {

                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        // TODO Auto-generated method stub
                        ch.pipeline().addLast(new MyHandlerRece());
                    }
                });
        try {
            Channel channel = bootstrap.bind(SPORT).sync().channel();
            channel.closeFuture().sync();
            group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


class MyHandlerRece extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {

        ByteBuf buf = msg.content();
        String strMsg = buf.toString(CharsetUtil.UTF_8);
        System.out.println("recv : "+strMsg);

        InetSocketAddress remoteAddress = new InetSocketAddress("255.255.255.255", UdpRecv.DPORT);
        ByteBuf byteBuf1 = new UnpooledByteBufAllocator(false).buffer();
        byteBuf1.writeCharSequence("recv send :"+strMsg, Charset.forName("utf-8"));
        DatagramPacket packet = new DatagramPacket(byteBuf1, remoteAddress);
        ctx.writeAndFlush(packet);
    }


}

