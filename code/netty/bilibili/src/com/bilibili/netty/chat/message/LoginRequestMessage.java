package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp6
 * @ClassName LoginRequestMessage
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/10-19:52
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public class LoginRequestMessage extends Message {
    private String username;

    private String password;


    public LoginRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;

    }

    @Override
    public int getMessageType() {
        return Message.LoginRequestMessage;
    }
}
