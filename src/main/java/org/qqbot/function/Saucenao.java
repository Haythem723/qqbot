package org.qqbot.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.qqbot.entity.SaucenaoResult;
import org.qqbot.utils.CommonUtil;
import org.qqbot.utils.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Saucenao {
  private static final String URL_BASE = "https://saucenao.com/search.php";
  private static final int DB = 5;
  private static final String KEY = "257501498bb01aebc1c5cd8e659b00a1a8545e8a";
  private static final int OUTPUT_TYPE = 2;
  private static final int NUMRES = 1;


  private static final Pattern resultPattern = Pattern.compile("\"results\": ?(\\[.*])");
  private static final Pattern errorMessagePattern = Pattern.compile("\"message\": ?\"(.*.)\"");
  private static final Pattern statusPattern = Pattern.compile("\"status\": ?(\\d+),\\s*\"r");

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

  public static SaucenaoResult getResult(String s) {
    String json;
    try {
      json = requestImg(s);
    } catch (IOException e) {
      e.printStackTrace();
      return handleError(null);
    }
    Matcher statusMatcher = statusPattern.matcher(json);
    if (!statusMatcher.find()) return handleError();
    String statusInt = statusMatcher.group(1);
    Integer integer = CommonUtil.parseInt(statusInt);
    if (integer == null) return handleError();
    if (integer != 0) {
      Matcher errorMatcher = errorMessagePattern.matcher(json);
      if (errorMatcher.find()) {
        String errorMsg = errorMatcher.group(1);
        return handleError(errorMsg);
      } else {
        return handleError();
      }
    }
    Matcher resMatcher = resultPattern.matcher(json);
    if (!resMatcher.find()) return handleError();
    String resString = resMatcher.group(1);
    ObjectMapper mapper = new ObjectMapper();
    SaucenaoResult[] results;
    try {
      results = mapper.readValue(resString, SaucenaoResult[].class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return new SaucenaoResult(-1, "返回值序列化异常");
    }
    SaucenaoResult result = results[0];
    result.setStatus(0);
    return result;
  }

  public static SaucenaoResult handleError() {
    return new SaucenaoResult(-1, "api返回格式异常");
  }

  public static SaucenaoResult handleError(String errorMsg) {
    return new SaucenaoResult(-1, "api调用失败: " + errorMsg);
  }
}