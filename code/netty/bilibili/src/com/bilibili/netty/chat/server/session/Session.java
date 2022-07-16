package com.bilibili.netty.chat.server.session;

import io.netty.channel.Channel;
/**
 * 会话管理接口
 */
public interface Session {
    /**
     * @description:绑定会话
     * @author:qianzx
     * @date：2022/7/11-21:09
     * @param: channel
     * @param: username
     **/
    void bind(Channel channel,String username);

    /**
     * @description:解绑会话
     * @author:qianzx
     * @date：2022/7/11-21:10
     * @param: channel
     **/
    void unBind(Channel channel);

    /**
     * @description:根据用户名获取Channel
     * @author:qianzx
     * @date：2022/7/11-21:12
     * @param: username
     * @return: io.netty.channel.Channel
     **/
    Channel getChannel(String username);

    /**
     * @description:
     * @author:qianzx
     * @date：2022/7/11-21:22
     * @param: channel
     * @param: username
     * @return: java.lang.Object
     **/
    Object getAttribute(Channel channel,String username);

    /**
     * @description:
     * @author:qianzx
     * @date：2022/7/11-21:22
     * @param: channel
     * @param: username
     * @param: value
     **/
    void setAttribute(Channel channel,String username,Object value);

}
