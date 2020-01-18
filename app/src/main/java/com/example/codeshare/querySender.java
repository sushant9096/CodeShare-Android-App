package com.example.codeshare;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class querySender extends Thread {

    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
    public static String str = "1";
    public static Boolean flag = false;
    int in = 0;
    String query;
    Object cc;
    URLConnection urlcon;

    querySender(String query, Object cc) {
        this.query = query;
        this.cc = cc;
    }

    public static char[] encodeHex(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }

    public static Boolean returnStatus() {
        return flag;
    }

    @Override
    public void run() {

        String hexString = String.valueOf(querySender.encodeHex(query.getBytes()));

        str = "";
        //String myURl = "http://pi.gunman.ml/gunman/api.php?c=" + query;
        String myURl = "http://shubhambadgujar222-52849.portmap.host:52849/codeshare/index.php?c=" + hexString;
        //handles exceptions like internet not connected
        System.out.println("Que = " + hexString);


        try {
            URL url = new URL(myURl);
            urlcon = url.openConnection(); //creating connection between client(game) and server
            InputStream stream = urlcon.getInputStream(); //Download data from server(server response)
            int i;
            str = "";
            while ((i = stream.read()) != -1) { //prints returned data character by character to the console
                //System.out.print((char) i);
                str = str + ((char) i);

            }

            System.out.println("Test : " + str);
            flag = true;

            //cc.speak(str);
            //Toast.makeText(cc, "String = "+str, Toast.LENGTH_SHORT).show();
            stream.close();
        } catch (Exception e) {
            System.out.println("Exception = " + e);
            str = "";
            //Toast.makeText(cc, "Error In Send Query : "+e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (urlcon != null) {
                urlcon = null;
            }
        }
    }


}
