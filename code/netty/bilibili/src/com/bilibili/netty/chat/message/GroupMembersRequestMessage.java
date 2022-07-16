package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName GroupMemberquestMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-19:55
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public class GroupMembersRequestMessage extends Message{
    private String groupName;

    public GroupMembersRequestMessage(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return 0;
    }
}
