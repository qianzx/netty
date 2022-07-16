package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName GroupChaRequeatMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-19:47
 * @Version 1.0
 */
@Data
@ToString
public class GroupChatRequestMessage extends Message{
    private String groupName;

    private String from;

    private String content;

    public GroupChatRequestMessage(String from,String groupName,String content) {
        this.from = from;
        this.groupName = groupName;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return Message.GroupChatRequestMessage;
    }
}
