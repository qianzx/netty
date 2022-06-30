package com.bilibili.netty.nio.cp2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.nio.cp2
 * @ClassName WriteClient
 * @Description TODO
 * @Author qianzx
 * @Date 2022/6/26-19:39
 * @Version 1.0
 */
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8080));
        int count = 0;
        while (true){
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            count += sc.read(buffer);
            System.out.println(count);
            buffer.clear();
        }

    }
}
