package com.qianzx.netty.cp9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-13 17:21:00
 */
public class AbsIntegerEncoderTest {
    @Test
    public void testEncoded(){
        ByteBuf buffer = Unpooled.buffer();
        for(int i = 1; i < 10;i++){
            buffer.writeInt(i * -1);
        }
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        channel.writeOutbound(buffer);
        channel.finish();

        for(int i = 1; i < 10;i++){
            System.out.println(channel.readOutbound());
        }
    }
}

