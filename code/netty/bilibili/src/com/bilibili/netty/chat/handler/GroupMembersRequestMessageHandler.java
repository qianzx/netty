package com.bilibili.netty.chat.handler;

import com.bilibili.netty.chat.message.GroupMembersRequestMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.handler
 * @ClassName GroupMemberRequestMessageHandler
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-23:13
 * @Version 1.0
 */
@ChannelHandler.Sharable

public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) throws Exception {

    }
}
