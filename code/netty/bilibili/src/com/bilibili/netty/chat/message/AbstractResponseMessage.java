package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName AbstractResponseMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/12-22:56
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)

public abstract class AbstractResponseMessage extends Message{
    private boolean success;
    private String reason;

    public AbstractResponseMessage() {
    }

    public AbstractResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}
