package com.qianzx.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.channels.SocketChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-05-20 13:30:00
 */
public class MyTest {
      public static void main(String[] args) throws IOException, ParseException {
         /* byte[] bytes = "1234567".getBytes();
          ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
          //intBuffer.position(4);
          CharBuffer charBuffer = byteBuffer.asCharBuffer();
          FileInputStream fileInputStream = new FileInputStream("c.txt");
          FileChannel channel = fileInputStream.getChannel();
          System.out.println(channel.isOpen());
          System.out.println(1);
          Thread thread = Thread.currentThread();
          boolean before = thread.isInterrupted();
          System.out.println(before);
          //thread.interrupt();
          System.out.println(channel.isOpen());
          boolean after = thread.isInterrupted();
          System.out.println(after);
          channel.read(byteBuffer);

          SocketChannel open = SocketChannel.open();
          int i = open.validOps();*/
          String str = "张";
          String ss = "AB";
          byte[] bytes = str.getBytes("utf-8");
          byte[] asciis = ss.getBytes("ASCII");
          byte[] gbks = str.getBytes("gbk");
          String utf8 = new String(bytes, "utf-8");
          String ascii = new String(asciis, "ASCII");
          String gbk = new String(gbks, "gbk");
          System.out.println(utf8);
          System.out.println(ascii);
          System.out.println(gbk);
      }
}

