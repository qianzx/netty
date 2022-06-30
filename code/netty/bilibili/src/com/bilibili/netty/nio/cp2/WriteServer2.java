package com.bilibili.netty.nio.cp2;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @BelongProject netty
 * @BelongPackage com.bilibili.netty.nio.cp2
 * @ClassName WriteServer2
 * @Description TODO
 * @Author qianzx
 * @Date 2022/6/26-19:51
 * @Version 1.0
 */
public class WriteServer2 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            selector.select();
            //下面代码耗时过长可能影响到其他channel，WriteServer2优化
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if(selectionKey.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey sckey = sc.register(selector, 0);
                    sckey.interestOps(SelectionKey.OP_READ);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0 ; i <=30000000;i++){
                        sb.append("a");
                    }

                    ByteBuffer buffer = CharsetUtil.UTF_8.encode(sb.toString());
                    int write = sc.write(buffer);
                    System.out.println(write);
                    if (buffer.hasRemaining()){
                        int opWrite = SelectionKey.OP_WRITE;
                        sckey.interestOps(sckey.interestOps() + opWrite);
                        sckey.attach(buffer);
                    }
                   }else if(selectionKey.isWritable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        int write = socketChannel.write(byteBuffer);
                        System.out.println(write);
                        if(!byteBuffer.hasRemaining()){
                            selectionKey.attach(null);
                            selectionKey.interestOps(selectionKey.interestOps() - SelectionKey.OP_WRITE);
                        }
                    }

            }
        }

    }

}
