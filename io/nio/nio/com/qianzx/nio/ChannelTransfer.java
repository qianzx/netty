package com.qianzx.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.*;
import java.util.Scanner;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-05-18 09:57:00
 */
public class ChannelTransfer {

      public static void main(String[] args) throws IOException {
        catFile(Channels.newChannel(System.out),new String[]{"d.txt","c.txt"});

      }

    private static void catFile(WritableByteChannel target,String[] files) throws IOException {
        for(int i = 0 ; i < files.length; i++){
            FileInputStream fileInputStream = new FileInputStream(files[i]);
            FileChannel channel = fileInputStream.getChannel();
            channel.transferTo(channel.position(),channel.size(),target);
            channel.close();
            fileInputStream.close();
        }
    }


}

