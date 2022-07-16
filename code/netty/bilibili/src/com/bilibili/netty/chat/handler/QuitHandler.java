package com.bilibili.netty.chat.handler;

import com.bilibili.netty.chat.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.handler
 * @ClassName QuitHandler
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/15-14:06
 * @Version 1.0
 */
@ChannelHandler.Sharable
@Slf4j
public class QuitHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionFactory.getSession().unBind(ctx.channel());
        log.debug("{}已断开连接",ctx.channel());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        SessionFactory.getSession().unBind(ctx.channel());
        log.debug("{}异常断开连接，异常是",ctx.channel(),cause.getMessage());
    }
}
