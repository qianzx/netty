package com.bilibili.netty.chat.message;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp6
 * @ClassName Message
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/10-19:11
 * @Version 1.0
 */
@Data
@ToString(callSuper = true)
public abstract class Message implements Serializable {

    private int sequenceId;

    private int messageType;


    public abstract int getMessageType();

    public static Class<?> getMessageClass(int messageType){
        return messageClass.get(messageType);
    }
    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;

    public static final int ChatRequestMessage = 2;
    public static final int ChatResponseMessage = 3;

    public static final int GroupCreateRequestMessage = 4;
    public static final int GroupCreateResponseMessage = 5;

    public static final int GroupJoinRequestMessage = 6;
    public static final int GroupJoinResponseMessage = 7;

    public static final int GroupQuitRequestMessage = 8;
    public static final int GroupQuitResponseMessage = 9;

    public static final int GroupChatRequestMessage = 10;
    public static final int GroupChatResponseMessage = 11;

    public static final int GroupMemberRequestMessage = 12;
    public static final int GroupMemberResponseMessage = 13;

    public static final int PingMessage = 12;
    public static final int PongMessage = 13;

    private static final Map<Integer,Class<?>> messageClass = new HashMap<>();

    static {
        messageClass.put(LoginRequestMessage, LoginRequestMessage.class);
        messageClass.put(LoginResponseMessage, LoginResponseMessage.class);
        messageClass.put(ChatRequestMessage, ChatRequestMessage.class);
        messageClass.put(ChatResponseMessage, ChatResponseMessage.class);
        messageClass.put(GroupCreateRequestMessage, GroupCreateRequestMessage.class);
        messageClass.put(GroupCreateResponseMessage, GroupCreateResponseMessage.class);
        messageClass.put(GroupJoinRequestMessage, GroupJoinRequestMessage.class);
        //messageClass.put(GroupJoinResponseMessage, GroupJoinResponseMessage.class);
        messageClass.put(GroupQuitRequestMessage, GroupQuitRequestMessage.class);
        //messageClass.put(GroupQuitResponseMessage, GroupQuitResponseMessage.class);
        messageClass.put(GroupChatRequestMessage, GroupChatRequestMessage.class);
        messageClass.put(GroupChatResponseMessage, GroupChatResponseMessage.class);
        messageClass.put(PingMessage, PingMessage.class);
        messageClass.put(PongMessage, PongMessage.class);

    }
}
