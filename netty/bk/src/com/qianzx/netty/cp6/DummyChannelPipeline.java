package com.qianzx.netty.cp6;

import io.netty.channel.*;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-10 15:41:00
 */
public class DummyChannelPipeline extends DefaultChannelPipeline {

    public static final ChannelPipeline DUMMY_INSTANCE = new DummyChannelPipeline(null);
    public DummyChannelPipeline(Channel channel) {
        super(channel);
    }
}

