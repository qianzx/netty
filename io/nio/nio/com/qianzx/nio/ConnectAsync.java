package com.qianzx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-05-18 16:40:00
 */
public class ConnectAsync {
      public static void main(String[] args) throws IOException {
             int port = 1234;
             String host = "localhost";
          InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
          SocketChannel channel = SocketChannel.open();
          channel.configureBlocking(false);
          System.out.println("initiating connection");
          channel.connect(inetSocketAddress);
          while (true){
              doSomeThingUseful();
          }

      }

    private static void doSomeThingUseful() {
        System.out.println("doing something useless");
    }
}

