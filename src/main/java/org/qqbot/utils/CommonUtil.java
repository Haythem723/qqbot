package org.qqbot.utils;

public class CommonUtil {
	public static Integer parseInt(String s) {
		try {
			return Integer.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}
}
