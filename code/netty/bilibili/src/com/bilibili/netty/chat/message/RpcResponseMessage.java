package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.chat.message
 * @ClassName RpcResponseMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/16-21:18
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public class RpcResponseMessage extends Message{
    private Object returnValue;

    private Exception exceptionValue;


    @Override
    public int getMessageType() {
        return Message.RpcResponseMessage;
    }
}
