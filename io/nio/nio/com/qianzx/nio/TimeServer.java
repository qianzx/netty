package com.qianzx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-05-19 10:00:00
 */
public class TimeServer {
    private static final int DEFAULT_TIME_PORT = 37;
    private static final long DIFF_1900 = 2208988000L;
    protected DatagramChannel channel;

    public TimeServer(int port) throws IOException {
        this.channel = DatagramChannel.open();
        this.channel.socket().bind(new InetSocketAddress(port));
        System.out.println ("Listening on port " + port
                + " for time requests");
    }

    public void listen() throws IOException {

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byteBuffer.putLong(0,0);
        byteBuffer.position(4);

        ByteBuffer sliceBuffer = byteBuffer.slice();
        while (true){
            sliceBuffer.clear();
            SocketAddress receive = this.channel.receive(sliceBuffer);
            if(receive == null){
               continue;
            }
            System.out.println("Time request from" + receive);
            byteBuffer.putLong(0,System.currentTimeMillis() / 1000 + DIFF_1900);
            this.channel.send(sliceBuffer,receive);
        }

    }

      public static void main(String[] args) throws IOException {
          int port = DEFAULT_TIME_PORT;
          try {
              TimeServer timeServer  = new TimeServer(port);
              timeServer.listen();

          } catch (SocketException e) {
              System.out.println ("Can't bind to port " + port
                      + ", try a different one");
          }

      }
}

