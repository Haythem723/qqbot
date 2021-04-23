package com.qqbot;


import com.qqbot.http.UrlResp;
import com.qqbot.http.Urllib;

import static com.qqbot.constant.MiraiConstant.BASE_URL;

public class Main {
  public static void main(String[] args) {
    UrlResp urlResp = Urllib.builder().setUrl(BASE_URL + "/about").get();

    if (urlResp.getResponseCode() == UrlResp.HTTP_OK) {
      System.out.println(urlResp.getText());
    }
  }
}
