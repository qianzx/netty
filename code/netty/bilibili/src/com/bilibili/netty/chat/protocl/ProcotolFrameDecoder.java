package com.bilibili.netty.chat.protocl;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.cp6.protocol
 * @ClassName ProcotolFrameDecoder
 * @Description TODO
 * @Author qianzx
 * @Date 2022/7/11-21:55
 * @Version 1.0
 */
public class ProcotolFrameDecoder extends LengthFieldBasedFrameDecoder {
    public ProcotolFrameDecoder() {
        this(1024,17,4,0,0);
    }

    public ProcotolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }


}
