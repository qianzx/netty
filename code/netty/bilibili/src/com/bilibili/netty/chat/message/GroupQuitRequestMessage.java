package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName GroupQuitRequestMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-19:58
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public class GroupQuitRequestMessage extends Message{
    private String username;
    private String groupName;

    public GroupQuitRequestMessage(String username,String groupName) {
        this.username = username;
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return Message.GroupQuitRequestMessage;
    }
}
