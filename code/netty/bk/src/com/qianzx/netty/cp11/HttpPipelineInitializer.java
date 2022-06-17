package com.qianzx.netty.cp11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-16 14:30:00
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        if(client){
            ch.pipeline().addLast("decoder",new HttpResponseDecoder());
            ch.pipeline().addLast("encoder",new HttpRequestEncoder());
        }else {
            ch.pipeline().addLast("decoder",new HttpResponseDecoder());
            ch.pipeline().addLast("encoder",new HttpResponseDecoder());
        }
    }
}

