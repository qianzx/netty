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

//���һ��Channel-Handler���Ա���Channel��ȫ����
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
    *����������쳣��ÿ��Channel����һ����֮�������ChannelPipeline��
    * �����һ��ChannelHandlerʵ������Ĭ�������ChannelHandler��Ѷ����ķ�������ת�������е���һ��ChannelHandler��
    * ���exceptionCaught()û�б�������ĳ��ʵ�֣���ô�����յ��쳣���ᱻ���ݵ�ChannelPipeline��β�˲�����¼��
    *
    * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

