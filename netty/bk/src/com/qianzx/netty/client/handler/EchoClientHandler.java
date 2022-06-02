package com.qianzx.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-05-31 17:46:00
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    //�ڵ�����˵������Ѿ�����֮�󱻵���
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("���ӳɹ�");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       /* System.out.println("1111111111111111");
        cause.printStackTrace();
        ctx.close();*/
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("�յ���Ϣ");
        System.out.println("Client recived:" + msg.toString(CharsetUtil.UTF_8));
    }
}

