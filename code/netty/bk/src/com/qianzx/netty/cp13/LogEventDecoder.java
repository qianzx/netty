package com.qianzx.netty.cp13;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-20 17:19:00
 */
public class LogEventDecoder extends MessageToMessageEncoder<DatagramPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        ByteBuf content = msg.content();
        int index = content.indexOf(0, content.readableBytes(), LogEvent.SEPARATOR);
        String fileName = content.slice(0, index).toString(CharsetUtil.UTF_8);
        String logMsg = content.slice(index + 1, content.readableBytes()).toString(CharsetUtil.UTF_8);
        System.out.println("-------handler-------:" + logMsg);
        out.add(new LogEvent(msg.sender(),System.currentTimeMillis(),fileName,logMsg));
    }
}

