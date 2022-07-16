package com.bilibili.netty.chat.message;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName PingMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/15-15:38
 * @Version 1.0
 */
public class PingMessage extends Message{

    @Override
    public int getMessageType() {
        return Message.PingMessage;
    }
}
