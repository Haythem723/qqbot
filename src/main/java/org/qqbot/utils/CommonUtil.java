package org.qqbot.utils;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.qqbot.constant.CommandType;
import org.qqbot.entity.Command;
import org.qqbot.entity.HelpListItem;
import org.qqbot.mapper.HelpMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	private static final Pattern dicePattern = Pattern.compile("/([0-9]+[dD][0-9]+)(.*)$");
	private static final Pattern commandPattern = Pattern.compile("/([\\u4e00-\\u9fa5]+|[a-zA-Z]+) ?(.*)$");

	public static Integer parseInt(String s) {
		try {
			return Integer.valueOf(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Command parseCommandAndArgs(String source) {
		Command command = new Command(CommandType.COMMAND_NULL);
		Matcher matcher = dicePattern.matcher(source);
		if (matcher.find()) {
			int count = matcher.groupCount();
			if (count >= 1 && !(matcher.group(1) != "")) {
				command.setType(CommandType.COMMAND_DICE);
				String[] args = new String[count];
				args[0] = matcher.group(1);
				command.setArgs(args);
				return command;
			}
		}
		matcher = commandPattern.matcher(source);
		if (matcher.find()) {

			int count = matcher.groupCount();
			String commands = matcher.group(1);
			SqlSession sqlSession = MybatisUtil.getSqlSession();
			HelpMapper mapper = sqlSession.getMapper(HelpMapper.class);
			HelpListItem item = mapper.getHelpListItem(commands);
			sqlSession.close();
			if (item != null) {
				CommandType type = CommandType.getTypeById(item.getId());
				command.setType(type);
			}
			if (count == 2 && (!matcher.group(2).equals(""))) {
				String arg = matcher.group(2);
				String[] args = arg.split(" ");
				command.setArgs(args);
			}
		}
		return command;
	}
}
