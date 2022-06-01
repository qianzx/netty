package com.qianzx.nio;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author qianzx
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022-05-30 11:02:00
 */
public class EncodeTest {
    public static void main (String [] argv) throws Exception {
        // This is the character sequence to encode
        String input = "ÄãºÃ";
        // the list of charsets to encode with
        String [] charsetNames = {
                "US-ASCII", "ISO-8859-1", "UTF-8", "UTF-16BE",
                "UTF-16LE", "UTF-16" // , "X-ROT13"
        };
        for (int i = 0; i < charsetNames.length; i++) {
            doEncode (Charset.forName (charsetNames [i]), input);
        }
    }
    /**
     * For a given Charset and input string, encode the chars
     * and print out the resulting byte encoding in a readable form.
     */
    private static void doEncode (Charset cs, String input) {
        ByteBuffer bb = cs.encode (input);
        System.out.println ("Charset: " + cs.name( ));
        System.out.println (" Input: " + input);
        System.out.println ("Encoded: ");

        for (int i = 0; bb.hasRemaining( ); i++) {
            int b = bb.get( );
            int ival = ((int) b) & 0xff;
            char c = (char) ival;
            // Keep tabular alignment pretty
            if (i < 10){
                System.out.print (" ");
            }
            // Print index number
            System.out.print (" " + i + ": ");
            // Better formatted output is coming someday...
            if (ival < 16){
                System.out.print ("0");
            }
            // Print the hex value of the byte
            System.out.print (Integer.toHexString (ival));
            // If the byte seems to be the value of a
            // printable character, print it. No guarantee
            // it will be.
            if (Character.isWhitespace (c) ||
                    Character.isISOControl (c))
            {
                System.out.println ("");
            } else {
                System.out.println (" (" + c + ")");
            }
        }
        System.out.println ("");
    }
}

