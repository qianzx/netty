package com.qianzx.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-05-31 14:57:00
 */

//标记一个Channel-Handler可以被个Channel安全共享
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("Server received:" + byteBuf.toString(CharsetUtil.UTF_8));
        //ctx.write(byteBuf);
        ReferenceCountUtil.release(msg);
        ctx.fireChannelRead(msg);

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);

    }
    /**
    *如果不捕获异常：每个Channel都有一个与之相关联的ChannelPipeline，
    * 其持有一个ChannelHandler实例链。默认情况下ChannelHandler会把对它的方法调用转发给链中的下一个ChannelHandler。
    * 因此exceptionCaught()没有被该链中某处实现，那么所接收的异常将会被传递到ChannelPipeline的尾端并被记录。
    *
    * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

