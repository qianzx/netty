package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName ChatResponseMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-21:56
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public class ChatResponseMessage extends Message{
    private boolean success;

    private String reson;
    private String from;

    private String content;

    public ChatResponseMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }

    public ChatResponseMessage(boolean success, String reson) {
        this.success = success;
        this.reson = reson;
    }

    @Override
    public int getMessageType() {
        return Message.ChatResponseMessage;
    }
}
