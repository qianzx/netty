package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName GroupJoinRequestMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-19:57
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public class GroupJoinRequestMessage extends Message{
    private String username;
    private String groupName;

    public GroupJoinRequestMessage(String username,String groupName) {
        this.username = username;
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return Message.GroupJoinRequestMessage;
    }
}
