package com.qqbot;

import com.qqbot.http.UrlResp;
import com.qqbot.http.Urllib;

import static com.qqbot.constant.MiraiConstant.HTTP_BASE_URL;

/**
 * @author diyigemt
 */
public class Bot {
	public static void main(String[] args) {
		UrlResp request = Urllib.builder()
				.setUrl(HTTP_BASE_URL + "/about")
				.get();
		if (request.getResponseCode() == UrlResp.HTTP_OK) {
			System.out.println(request.getText());
		}
	}
}
