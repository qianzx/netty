package com.qianzx.nio;

import io.netty.buffer.ByteBufUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-06-08 09:11:00
 */
public class Server {
      public static void main(String[] args) throws IOException {
          ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
          serverSocketChannel.configureBlocking(false);
          serverSocketChannel.bind(new InetSocketAddress(9999));
          Selector selector = Selector.open();
          serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
          while (selector.select() > 0){
              Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
              while (iterator.hasNext()){
                  SelectionKey selectionKey = iterator.next();
                  if(selectionKey.isAcceptable()){
                      ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
                      SocketChannel channel = socketChannel.accept();
                      channel.configureBlocking(false);
                      channel.register(selector,SelectionKey.OP_READ);
                  }
                  if(selectionKey.isReadable()){
                      SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                      ByteBuffer allocate = ByteBuffer.allocate(1024);
                      int len;
                      while ((len = socketChannel.read(allocate)) > 0){
                          allocate.flip();
                          String msg = new String(allocate.array(), 0, allocate.remaining());
                          System.out.println(msg);
                          allocate.clear();
                      }
                      iterator.remove();
                  }
              }
          }

      }
}

