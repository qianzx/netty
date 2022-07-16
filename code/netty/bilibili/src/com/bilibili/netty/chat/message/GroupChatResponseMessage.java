package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName GroupChatResponseMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/15-12:59
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public class GroupChatResponseMessage extends Message{
    private String from;

    private String content;

    public GroupChatResponseMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return Message.GroupChatResponseMessage;
    }
}
