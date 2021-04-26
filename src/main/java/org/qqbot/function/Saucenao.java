package org.qqbot.function;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Saucenao {
    private static final String URL_BASE = "https://saucenao.com/search.php";
    private static final int DB = 999;
    private static final String KEY = "257501498bb01aebc1c5cd8e659b00a1a8545e8a";
    private static final int OUTPUT_TYPE = 2;
    private static final int NUMRES = 1;

    public static String requestImg (String url) throws IOException {
        String URL_REQUEST = URL_BASE + "?url=" + url + "&db=" + DB + "&key=" + KEY + "&output_type=" + OUTPUT_TYPE + "&numres=" + NUMRES;
        String res = null;
        InputStream in = new HttpUtil().getInputStream(URL_REQUEST);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte buf[] = new byte[1024];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        } finally {
            if ( in != null) {
                in .close();
            }
        }
        res = out.toString();
        return res;
    }

    public static String getImg(String s){
        try{
            return requestImg(s);

        }catch (Exception e){
            e.printStackTrace();
            return "ERR";
        }
    }
}
