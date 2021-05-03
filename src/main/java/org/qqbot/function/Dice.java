package org.qqbot.function;

import org.qqbot.entity.DiceLogItem;
import org.qqbot.entity.DiceResultItem;
import org.qqbot.mapper.DiceMapper;
import org.qqbot.utils.MybatisUtil;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据参数得到骰子结果字符串
 * @author HayTmey
 */
public class Dice {

	private static final Pattern dicePattern = Pattern.compile("^([0-9]+)([dD*])([0-9]+)$");

	/**
	 * 根据参数获取骰子字符串
	 * @param time 骰子数量
	 * @param face 骰子面数
	 * @return 结果字符串
	 */
	private static DiceResultItem roll(int time, int face, long seed) {
		Random random = new Random();
		DiceResultItem item = new DiceResultItem();
		random.setSeed(seed);
		if (time == 1) {
			int res = random.nextInt(face) + 1;
			return item.setResultSum(res).setResultString(String.valueOf(res));
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
		String s = sb.append(" = ").append(sum).toString();
		return item.setResultSum(sum).setResultString(s);
	}

	/**
	 * 根据指令原始参数获取结果
	 * @param rollString 原始参数
	 * @param senderName 发送方名片
	 * @param senderId 发送方id
	 * @return 结果
	 */
	public static DiceResultItem getRoll(String rollString, String senderName, String senderId) { //可接受输入：1d10、1D10
		Matcher matcher = dicePattern.matcher(rollString);
		if (!matcher.find() || matcher.groupCount() < 3) return new DiceResultItem().setResultString("非法输入");//用户的锅
		String d = matcher.group(2);
		try {
			int time = Integer.parseInt(matcher.group(1));
			int face = Integer.parseInt(matcher.group(3));
			Long seed = System.nanoTime();
			DiceResultItem res = roll(time, face, seed);
			StringBuilder sb = new StringBuilder();
			sb.append(time);
			sb.append(d.equals("d") ? "d" : "D")
					.append(face)
					.append(" = ")
					.append(res.getResultString());
			String s = sb.toString();
			appendDiceLog(s, senderName, senderId, seed);
			return res.setResultString(s);// 输出结果eg: 1d10 = 2
		} catch (NumberFormatException | StringIndexOutOfBoundsException e) {
			return new DiceResultItem().setResultString("非法输入");//用户的锅
		} catch (Exception e) {
			return new DiceResultItem().setResultString("Roll模块出错，请联系管理员");//我的锅
		}
	}

	private static int appendDiceLog(String res, String senderName, String senderId, long seed) {
		DiceLogItem item = new DiceLogItem();
		item.setSenderId(senderId);
		item.setSenderName(senderName);
		item.setTimestamp(new Date());
		item.setLog(res + ", seed: " + seed);
		return MybatisUtil.getInstance().insetData(DiceMapper.class, DiceLogItem.class, "insertDiceLog", item);
	}
}
