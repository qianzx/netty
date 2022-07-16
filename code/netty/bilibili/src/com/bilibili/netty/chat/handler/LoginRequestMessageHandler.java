package com.bilibili.netty.chat.handler;

import com.bilibili.netty.chat.message.LoginRequestMessage;
import com.bilibili.netty.chat.message.LoginResponseMessage;
import com.bilibili.netty.chat.server.service.UserService;
import com.bilibili.netty.chat.server.service.UserServiceFactory;
import com.bilibili.netty.chat.server.session.Session;
import com.bilibili.netty.chat.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.server
 * @ClassName LoginRequestMessageHandler
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/13-21:47
 * @Version 1.0
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        UserService userService = UserServiceFactory.getUserService();
        boolean login = userService.login(msg.getUsername(), msg.getPassword());
        LoginResponseMessage responseMessage;
        if (login) {
            responseMessage = new LoginResponseMessage(true, "登录成功");
            Session session = SessionFactory.getSession();
            session.bind(ctx.channel(), msg.getUsername());
        } else {
            responseMessage = new LoginResponseMessage(false, "登录失败");

        }
        ctx.writeAndFlush(responseMessage);
    }
}
