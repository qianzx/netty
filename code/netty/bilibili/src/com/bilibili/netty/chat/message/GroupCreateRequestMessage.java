package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName GroupCreateRequestMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-19:51
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public class GroupCreateRequestMessage extends Message{
    private String groupName;

    private Set<String> members;

    public GroupCreateRequestMessage(String groupName, Set<String> members) {
        this.groupName = groupName;
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return Message.GroupCreateRequestMessage;
    }
}
