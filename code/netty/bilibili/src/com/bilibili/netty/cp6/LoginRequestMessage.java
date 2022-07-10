package com.bilibili.netty.cp6;

import lombok.Data;

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
public class LoginRequestMessage extends Message{
    private String username;

    private String password;

    private String nickname;

    public LoginRequestMessage(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    @Override
    public int getMessageType() {
        return Message.LoginRequestMessage;
    }
}
