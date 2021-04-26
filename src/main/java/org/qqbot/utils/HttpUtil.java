package org.qqbot.utils;

import org.qqbot.constant.ConstantHttp;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HttpUtil {
	public InputStream getInputStream(String urlString) {
		InputStream inputStream = null;
		try {
			URL url = new URL(urlString);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Host", ConstantHttp.HEADER_HOST);
			connection.setRequestProperty("referer", ConstantHttp.HEADER_REFERER);
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("cookie", ConstantHttp.HEADER_COOKIE);
			connection.setRequestProperty("User-agent", ConstantHttp.HEADER_USER_AGENT);
			connection.connect();
			inputStream = connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
}