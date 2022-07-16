package com.bilibili.netty.chat.handler;

import com.bilibili.netty.chat.message.GroupQuitRequestMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.handler
 * @ClassName GroupQuitRequestMessageHandler
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-23:12
 * @Version 1.0
 */
@ChannelHandler.Sharable

public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {

    }
}
