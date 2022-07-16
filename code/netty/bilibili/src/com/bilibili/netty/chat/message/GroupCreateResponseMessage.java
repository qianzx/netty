package com.bilibili.netty.chat.message;

import lombok.Data;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName GroupCreateResponseMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-23:35
 * @Version 1.0
 */
@Data
public class GroupCreateResponseMessage extends Message{
    private boolean success;

    private String reason;

    public GroupCreateResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public int getMessageType() {
        return Message.GroupCreateResponseMessage;
    }
}
