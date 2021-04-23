package com.qqbot;

import com.alibaba.fastjson.JSONObject;
import com.qqbot.http.UrlResp;
import com.qqbot.http.Urllib;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import static com.qqbot.constant.MiraiConstant.BASE_URL;

public class SimpleSendMessageTest {
	public static void main(String[] args) {
		UrlResp urlResp = Urllib.builder().setUrl(BASE_URL + "/about").get();

		if (urlResp.getResponseCode() == UrlResp.HTTP_OK) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("authKey", "DOUBLEKEYdiyigemt");
			urlResp = Urllib.builder().setUrl(BASE_URL + "/auth").setPostData(map).post();
			if (urlResp.getResponseCode() == UrlResp.HTTP_OK) {
				Map map1 = JSONObject.parseObject(urlResp.getText());
				String session = (String) map1.get("session");
				map.clear();
				map.put("sessionKey", session);
				map.put("qq", "2492921801");
				urlResp = Urllib.builder().setUrl(BASE_URL + "/verify").setPostData(map).post();
				if (urlResp.getResponseCode() == UrlResp.HTTP_OK) {
					map.clear();
					map.put("sessionKey", session);
					map.put("target", "1002484182");
					map.put("messageChain", "[{\"type\": \"Plain\", \"text\": \"Hello\"}]");
					try {
						URL url = new URL(BASE_URL + "/sendGroupMessage");
						HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
						urlConnection.setRequestMethod("POST");
						urlConnection.setDoOutput(true);
						OutputStream outputStream = urlConnection.getOutputStream();
						Object o = JSONObject.toJSON(map);
						outputStream.write(o.toString().getBytes());
						InputStream inputStream = urlConnection.getInputStream();
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
						final StringBuffer buffer = new StringBuffer();
						String line = null;
						while ((line = bufferedReader.readLine()) != null) {
							buffer.append(line);
						}
						System.out.println(buffer.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
//					urlResp = Urllib.builder().setUrl(BASE_URL + "/sendGroupMessage").setPostData(map).post();
//					if (urlResp.getResponseCode() == UrlResp.HTTP_OK) {
//						System.out.println(urlResp.getText());
//					}
				}
			}
		}
	}
}
