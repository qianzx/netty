package com.bilibili.netty.cp5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp5
 * @ClassName TestLenrthFieldDecoder
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/7-20:13
 * @Version 1.0
 */
public class TestLenrthFieldDecoder {
    public static void main(String[] args) {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024 * 10,
                        0,4,4,8),
                new LoggingHandler(LogLevel.DEBUG)
        );

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(2048);
        send(buffer,"hello world");
        send(buffer,"Hi");

        embeddedChannel.writeInbound(buffer);

    }

    private static void send(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        buffer.writeInt(length);
        byte[] bytes1 = "v1.0".getBytes(StandardCharsets.UTF_8);
        buffer.writeBytes(bytes1);
        buffer.writeBytes(bytes);
    }
}
