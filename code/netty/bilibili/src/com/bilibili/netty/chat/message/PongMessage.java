package com.bilibili.netty.chat.message;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName PongMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/15-15:39
 * @Version 1.0
 */
public class PongMessage extends Message{
    @Override
    public int getMessageType() {
        return PongMessage;
    }
}
