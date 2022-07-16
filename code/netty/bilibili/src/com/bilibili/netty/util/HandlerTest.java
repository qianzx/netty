package com.bilibili.netty.util;

import com.bilibili.netty.chat.message.LoginRequestMessage;
import com.bilibili.netty.chat.protocl.MessageCodecSharable;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.util
 * @ClassName HandlerTest
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/16-12:57
 * @Version 1.0
 */
public class HandlerTest {
    public static void main(String[] args) {
        LoggingHandler loggingHandler = new LoggingHandler();
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123456");
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(loggingHandler, messageCodecSharable, loggingHandler);
        embeddedChannel.writeOutbound(message);
    }
}
