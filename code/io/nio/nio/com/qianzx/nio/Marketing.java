package com.qianzx.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-05-16 10:48:00
 */
public class Marketing {
    private static final String DEMOGRAPHIC = "blahblah.txt";
    public static void main (String [] argv) throws Exception {
        int reps = 10;
        if (argv.length > 0) {
            reps = Integer.parseInt (argv [0]);
        }
        FileOutputStream fos = new FileOutputStream (DEMOGRAPHIC);
        GatheringByteChannel gatherChannel = fos.getChannel( );
        ByteBuffer [] bs = utterBS (reps);
        gatherChannel.write (bs);
        System.out.println ("Mindshare paradigms synergized to "
                + DEMOGRAPHIC);
        
        fos.close( );

    }

    private static String [] col1 = {
            "Aggregate", "Enable", "Leverage",
            "Facilitate", "Synergize", "Repurpose",
            "Strategize", "Reinvent", "Harness"
    };
    private static String [] col2 = {
            "cross-platform", "best-of-breed", "frictionless",
            "ubiquitous", "extensible", "compelling",
            "mission-critical", "collaborative", "integrated"
    };
    private static String [] col3 = {
            "methodologies", "infomediaries", "platforms",
            "schemas", "mindshare", "paradigms",
            "functionalities", "web services", "infrastructures"
    };
    private static String newline = System.getProperty ("line.separator");

    private static ByteBuffer[] utterBS (int howMany)
            throws Exception
    {
        List list = new LinkedList( );
        for (int i = 0; i < howMany; i++) {
            list.add (pickRandom (col1, " "));
            list.add (pickRandom (col2, " "));
            list.add (pickRandom (col3, newline));
        }
        ByteBuffer [] bufs = new ByteBuffer [list.size( )];
        list.toArray (bufs);
        return bufs;
    }
    private static Random rand = new Random();

    private static ByteBuffer pickRandom (String [] strings, String suffix)
            throws Exception
    {
        String string = strings [rand.nextInt (strings.length)];
        int total = string.length() + suffix.length( );
        ByteBuffer buf = ByteBuffer.allocate (total);
        buf.put (string.getBytes ("US-ASCII"));
        buf.put (suffix.getBytes ("US-ASCII"));
        buf.flip( );
        return (buf);
    }

}

