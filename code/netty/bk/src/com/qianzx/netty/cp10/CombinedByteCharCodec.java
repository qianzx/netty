package com.qianzx.netty.cp10;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-16 11:02:00
 */
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ToIntegerDecoder,ShortToByteEncoder> {
    public CombinedByteCharCodec() {
        super(new ToIntegerDecoder(),new ShortToByteEncoder());
    }
}

