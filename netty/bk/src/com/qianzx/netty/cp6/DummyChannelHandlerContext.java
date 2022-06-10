package com.qianzx.netty.cp6;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.util.concurrent.EventExecutor;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-10 15:31:00
 */
public class DummyChannelHandlerContext extends io.netty.channel.AbstractChannelHandlerContext {
    public static ChannelHandlerContext DUMMY_INSTANCE = new DummyChannelHandlerContext(
            null,
            null,
            null,
            true,
            true
    );
    public DummyChannelHandlerContext(DefaultChannelPipeline pipeline,
                                      EventExecutor executor,
                                      String name, boolean inbound, boolean outbound) {
        super(pipeline, executor, name, inbound, outbound);
    }

    @Override
    public ChannelHandler handler() {
        return null;
    }
}

