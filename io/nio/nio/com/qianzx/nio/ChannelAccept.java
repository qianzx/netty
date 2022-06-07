package com.qianzx.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-05-18 14:43:00
 */
public class ChannelAccept {
    private static final String GREETING = "Hello I must be going.\\r\\n";

      public static void main(String[] args) throws IOException, InterruptedException {
          int port = 1234;
          DatagramChannel open = DatagramChannel.open();

          ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
          ServerSocketChannel socketChannel = ServerSocketChannel.open();
          ServerSocket socket = socketChannel.socket();
          socket.bind(new InetSocketAddress(port));
          socketChannel.configureBlocking(false);
          while (true){
              System.out.println("Waiting for connections");
              SocketChannel channel = socketChannel.accept();
              Socket accept = socket.accept();
              if(channel == null){
                  // no connections, snooze a while
                Thread.sleep(2000);
              }else {
                  System.out.println ("Incoming connection from: "
                          + channel.socket().getRemoteSocketAddress( ));
                buffer.rewind();
                channel.write(buffer);
                channel.close();
              }
          }
      }
}

