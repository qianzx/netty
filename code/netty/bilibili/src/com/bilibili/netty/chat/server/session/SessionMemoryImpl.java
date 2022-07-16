package com.bilibili.netty.chat.server.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.demo.server.session
 * @ClassName SessionMemoryImpl
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/11-21:14
 * @Version 1.0
 */
public class SessionMemoryImpl implements Session{
    private static final Map<String,Channel> usernameChannelMap = new ConcurrentHashMap();
    private static final Map<Channel,String> channeUsernamelMap = new ConcurrentHashMap();
    private static final Map<Channel,Map<String,Object>> channelAttributeMap = new ConcurrentHashMap();
    @Override
    public void bind(Channel channel, String username) {
        usernameChannelMap.put(username,channel);
        channeUsernamelMap.put(channel,username);
        channelAttributeMap.put(channel,new ConcurrentHashMap<>());
    }

    @Override
    public void unBind(Channel channel) {
        String username = channeUsernamelMap.remove(channel);
        usernameChannelMap.remove(username);
        channelAttributeMap.remove(channel);
    }

    @Override
    public Channel getChannel(String username) {
        return usernameChannelMap.get(username);
    }

    @Override
    public Object getAttribute(Channel channel, String username) {
        return channelAttributeMap.get(channel);
    }

    @Override
    public void setAttribute(Channel channel, String username, Object value) {
        channelAttributeMap.put(channel,new ConcurrentHashMap(){{
            put(username,value);
        }});
    }
}
