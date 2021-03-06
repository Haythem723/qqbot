package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Promise;
import org.qqbot.constant.CommandType;
import org.qqbot.constant.ConstantJoke;
import org.qqbot.entity.Command;
import org.qqbot.entity.JokeLibItem;
import org.qqbot.mapper.JokeMapper;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.CommonUtil;
import org.qqbot.utils.MybatisUtil;
import org.qqbot.utils.SimplePromise;

import java.util.ArrayList;
import java.util.Random;

public class CommandJoke implements CommandInvoker {
	@Override
	public Promise invoke(MessageEvent event, Command command) {
		ArrayList<String> args = command.getArgs();
		if (args.size() != 0) return new CommandHelp().invoke(event, command.setType(CommandType.COMMAND_HELP).setHelpVirtualId(CommandType.COMMAND_GET_HAPPY.getIndex()));
		int id = new Random(System.nanoTime()).nextInt(ConstantJoke.MAXIMUM_JOKE_LIB + 1);
		return new SimplePromise<String>(deferred -> {
			JokeLibItem jokeLibItem = MybatisUtil.getInstance().getSingleData(JokeMapper.class, JokeLibItem.class, "getJoke", String.valueOf(id));
			if (jokeLibItem == null) {
				deferred.reject(CommonUtil.getCommandFailInfo(command, "jokeId: " + id));
				return;
			}
			String res = jokeLibItem.toString();
			deferred.resolve(res);
		}).then(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}).fail(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		});
	}
}
