package org.qqbot.function;

import java.util.Random;

/**
 * 根据参数得到骰子结果字符串
 * @author HayTmey
 */
public class Dice {
	/**
	 * 根据参数获取骰子字符串
	 * @param time 骰子数量
	 * @param face 骰子面数
	 * @return 结果字符串
	 */
	private static String roll(int time, int face) {
		Random random = new Random();
		Long seed = System.nanoTime();
		random.setSeed(seed);
		if (time == 1) {
			return String.valueOf(random.nextInt(face) + 1);
		}
		StringBuilder sb = new StringBuilder();
		int sum = 0;
		for (int i = 0; i < time; i++) {
			int res = random.nextInt(face) + 1;
			sum += res;
			sb.append(res);
			if (i + 1 == time) continue;
			sb.append("+");
			seed = System.nanoTime();
			random.setSeed(seed);
		}
		return sb.append(" = ").append(sum).toString();
	}

	/**
	 * 根据指令原始参数获取结果
	 * @param s 原始参数
	 * @return 结果
	 */
	public static String getRoll(String s) { //可接受输入：1d10、1D10
		String temp = s.toLowerCase();
		try {
			int time = Integer.parseInt(temp.substring(0, temp.indexOf("d")));
			int face = Integer.parseInt(temp.substring(temp.indexOf("d") + 1));
			String res = roll(time, face);
			return s + " = " + res;// 输出结果eg: 1d10 = 2
		} catch (NumberFormatException | StringIndexOutOfBoundsException e) {
			return "非法输入";//用户的锅
		} catch (Exception e) {
			return "Roll模块出错，请联系管理员";//我的锅
		}
	}
}
