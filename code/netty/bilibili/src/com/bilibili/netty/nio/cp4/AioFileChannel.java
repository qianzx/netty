package com.bilibili.netty.nio.cp4;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.nio.cp4
 * @ClassName AioFileChannel
 * @Description TODO
 * @Author qianzx
 * @Date 2022/6/30-20:18
 * @Version 1.0
 */
public class AioFileChannel {
    public static void main(String[] args) {
        try {
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName());
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("data.txt"), StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(16);
            System.out.println("read begin...........");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.println("read completed.....");
                    Thread thread = Thread.currentThread();
                    System.out.println(thread.isDaemon());
                    System.out.println(thread.getName());
                    attachment.flip();
                    System.out.println(Charset.defaultCharset().decode(attachment));
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {

                }
            });
            System.out.println("read end..........");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
