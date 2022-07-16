package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.message
 * @ClassName LoginResponseMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/12-22:58
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public class LoginResponseMessage extends AbstractResponseMessage{
    public LoginResponseMessage() {
    }

    public LoginResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return LoginResponseMessage;
    }
}
