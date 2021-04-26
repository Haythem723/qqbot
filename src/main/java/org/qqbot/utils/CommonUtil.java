package org.qqbot.utils;

import org.qqbot.constant.CommandType;
import org.qqbot.entity.Command;
import org.qqbot.entity.HelpListItem;
import org.qqbot.mapper.HelpMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author diyigemt
 * 通用工具类
 */
public class CommonUtil {
	private static final Pattern dicePattern = Pattern.compile("/([0-9]+[dD][0-9]+)(.*)$");
	private static final Pattern commandPattern = Pattern.compile("/([\\u4e00-\\u9fa5]+|[a-zA-Z]+) ?(.*)$");

	/**
	 * 将字符串转换成Integer 是吧捕获异常并返回null
	 *
	 * @param s 要转换的字符串
	 * @return 结果
	 */
	public static Integer parseInt(String s) {
		try {
			return Integer.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 核心功能 将指令字符串通过正则分离 指令 和 参数
	 *
	 * @param source 原字符串
	 * @return 分离出的指令 和 参数列表
	 */
	public static Command parseCommandAndArgs(String source) {
		// 默认为空指令
		Command command = new Command(CommandType.COMMAND_NULL);
		// 判断是否是骰子指令的缩写
		Matcher matcher = dicePattern.matcher(source);
		if (matcher.find()) {
			int count = matcher.groupCount();
			// 由于 (.*) 的存在 导致会多一个 "" 参数 需要过滤
			if (count >= 1 && !(matcher.group(1).equals(""))) {
				command.setType(CommandType.COMMAND_DICE);
				String arg = matcher.group(1);
				command.addArgs(arg);
				return command;
			}
		}
		// 判断其他参数
		matcher = commandPattern.matcher(source);
		if (matcher.find()) {
			int count = matcher.groupCount();
			String commands = matcher.group(1);
			System.out.println(commands);
			// 获取参数对应的参数序号
			// TODO 模糊搜索
			HelpListItem item = MybatisUtil.getInstance().getSingleData(HelpMapper.class, HelpListItem.class, "getHelpListItem", commands);
			if (item == null) {
				return command.setType(CommandType.COMMAND_HELP);
			}
			CommandType type = CommandType.getTypeById(item.getId());
			command.setType(type);
			// 由于 (.*) 的存在 导致会多一个 "" 参数 需要过滤
			if (count == 2 && (!matcher.group(2).equals(""))) {
				String arg = matcher.group(2);
				String[] args = arg.split(" ");
				for (String s : args) {
					if (s.equals("")) continue;
					command.addArgs(s);
				}
			}
		}
		return command;
	}

	public static String getCommandFailInfo(Command command, Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append("在执行 ")
				.append(command.toString())
				.append(" 时失败\nexception")
				.append(e.getClass())
				.append(": ")
				.append(e.getMessage())
				.append("\n");
		return sb.toString();
	}

	public static String getCommandFailInfo(Command command, String msg) {
		StringBuilder sb = new StringBuilder();
		sb.append("在执行 ")
				.append(command.toString())
				.append(" 时失败\nmsg: ")
				.append(msg)
				.append("\n");
		return sb.toString();
	}
}
