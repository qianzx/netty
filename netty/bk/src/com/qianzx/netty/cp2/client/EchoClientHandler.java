package com.qianzx.netty.cp2.client;

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
 * @createTime 2022-06-09 13:44:00
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    //从服务器收到消息被调用
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg){
        System.out.println(
                "Client received: " + msg.toString(CharsetUtil.UTF_8));
    }

    //到服务器的连接建立之后被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ctx.write(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

