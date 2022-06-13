package com.qianzx.netty.cp9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import sun.awt.EmbeddedFrame;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-13 17:41:00
 */
public class FixedLengthFrameDecoderTest {
    @Test
    public void testFramesDecoded(){
        ByteBuf buffer = Unpooled.buffer();
        for(int i = 0;i > 0;i++){
            buffer.writeByte(i);
        }
        ByteBuf input = buffer.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        channel.writeInbound(input.retain());
        channel.finish();

        ByteBuf read = (ByteBuf) channel.readInbound();

        ByteBuf slice = buffer.readSlice(3);
        read.release();
    }
}

