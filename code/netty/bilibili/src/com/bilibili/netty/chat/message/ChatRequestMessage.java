package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName ChatRequestMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-19:45
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public class ChatRequestMessage extends Message{
    private String from;

    private String to;

    private String content;

    public ChatRequestMessage(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return Message.ChatRequestMessage;
    }
}
