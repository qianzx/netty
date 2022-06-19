package com.qianzx.bf;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
/**
* 黏包 半包
* */
public class StickyHalfBag {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI`m zhangsan\nHo".getBytes(StandardCharsets.UTF_8));
        split(source);
        source.put("w are you?\n".getBytes(StandardCharsets.UTF_8));
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for(int i = 0;i < source.limit();i++){
            if(source.get(i) == '\n'){
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for(int j = 0; j < length; j++){
                    target.put(source.get());
                }
            }
        }
        source.compact();
    }
}
