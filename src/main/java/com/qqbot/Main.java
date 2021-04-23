package com.qqbot;


import com.qqbot.http.UrlResp;
import com.qqbot.http.Urllib;

import javax.sql.rowset.spi.SyncResolver;
import java.util.*;

import static com.qqbot.constant.MiraiConstant.*;

public class Main {
  private static String sessionKey = null;

  public static void main(String[] args) {
    sessionKey = login();
    text();
    logout(sessionKey);
  }

  public static String login(){
    UrlResp resp = Urllib.builder()
            .setUrl(BASE_URL + "/auth")
            .addPostData("authKey", AUTH)
            .post();

    if(resp.getResponseCode() == UrlResp.HTTP_OK){//获取并绑定SessionKey
      String info = resp.getText();
      String sessionkey = null;
      sessionkey = info.substring(info.lastIndexOf(",") + 12, info.length() - 2);
      resp = Urllib.builder()
              .setUrl(BASE_URL + "/verify")
              .addPostData("sessionKey", sessionkey)
              .addPostData("qq", QQ)//机器人QQ号
              .post();
      if(resp.getResponseCode() == UrlResp.HTTP_OK){
        System.out.println("绑定结果： " + resp.getText());
      }
      return sessionkey;
    }
    else return "Err";
  }

  public static void logout(String sessionKey){//程序结束时调用，释放SessionKey
    UrlResp resp = Urllib.builder()
            .setUrl(BASE_URL + "/release")
            .addPostData("sessionKey", sessionKey)
            .addPostData("qq", QQ)
            .post();
    if(resp.getResponseCode() == UrlResp.HTTP_OK){
      System.out.println("释放结果： " + resp.getText());
    }
  }

  public static void text(){
    System.out.println(msgBuilder("yyds"));
    UrlResp resp = Urllib.builder()
            .setUrl(BASE_URL + "/sendGroupMessage")
            .addPostData("sessionKey", sessionKey)
            .addPostData("target", GroupNumber)
            .addPostData("messageChain", msgBuilder("yyds").toArray())
            .post();
    if(resp.getResponseCode() == UrlResp.HTTP_OK){
      System.out.println("消息发送结果： " + resp.getText());
    }
  }

//  public static String[] msgBuilder(String s){
//    String[] temp = new String[2];
//    temp[0] = "{ \"type\": \"Plain\", \"text\": \"" + s + "\\n\" }";
//    temp[1] = "{ \"type\": \"Plain\", \"text\": \"" + "hello" + "\" }";
//    return temp;
//  }
  public static List<String> msgBuilder(String s){
    List<String> list = new ArrayList<>();
    list.add("{ \"type\": \"Plain\", \"text\": \"" + s + "\" }");
    return list;
  }

//  public static String msgBuilder(String s){
//    List list = new ArrayList();
//    list.add("{ \"type\": \"Plain\", \"text\": \"" + s + "\\n\" }");
//    list.add("{ \"type\": \"Plain\", \"text\": \"" + s + "\\n\" }");
////    temp[0] = "{ \"type\": \"Plain\", \"text\": \"" + s + "\\n\" }";
////    temp[1] = "{ \"type\": \"Plain\", \"text\": \"" + "hello" + "\" }";
//    return Urllib.builder().toJSONString(list);
//  }

//  public static List<Map<String, Object>> msgBuilder(String s){
//    Map<String, Object> type = new LinkedHashMap<>();
//    type.put("\"type\"", "\"Plain\"");
//    Map<String, Object> text = new LinkedHashMap<>();
//    text.put("\"text\"", "\"" + s + "\"");
//    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//    list.add(type);
//    list.add(text);
//    return list;
//  }

//  public static String msgBuilder(String s){
//    Map data = new HashMap();
//
//    Map keyword1 = new HashMap();
//    keyword1.put("text", "Plain");
//    data.put("keyword1", keyword1);
//
//    Map keyword2 = new HashMap();
//    keyword1.put("text", s);
//    data.put("keyword2", keyword1);
//    return Urllib.builder().toJSONString(data);
//  }
}
