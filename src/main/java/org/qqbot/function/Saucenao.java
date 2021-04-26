package org.qqbot.function;

import org.qqbot.utils.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Saucenao {
  private static final String URL_BASE = "https://saucenao.com/search.php";
  private static final int DB = 999;
  private static final String KEY = "257501498bb01aebc1c5cd8e659b00a1a8545e8a";
  private static final int OUTPUT_TYPE = 2;
  private static final int NUMRES = 1;

  public static String requestImg(String url) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append(URL_BASE)
        .append("?url=")
        .append(url)
        .append("&db=")
        .append(DB)
        .append("&api_key=")
        .append(KEY)
        .append("&output_type=")
        .append(OUTPUT_TYPE)
        .append("&numres=")
        .append(NUMRES);
    String requestUrl = sb.toString();
    String res = null;
    InputStream in = new HttpUtil().getInputStream(requestUrl);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      byte buf[] = new byte[1024];
      int read = 0;
      while ((read = in.read(buf)) > 0) {
        out.write(buf, 0, read);
      }
    } finally {
      if (in != null) {
        in.close();
      }
    }
    res = out.toString();
    return res;
  }

  public static String getImg(String s) {
    try {
      return requestImg(s);
    } catch (Exception e) {
      e.printStackTrace();
      return "ERR";
    }
  }
}
