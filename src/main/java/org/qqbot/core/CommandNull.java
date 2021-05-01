package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Promise;
import org.qqbot.entity.Command;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.SimplePromise;

public class CommandNull implements CommandInvoker {
	@Override
	public Promise<String, String, String> invoke(MessageEvent event, Command command) {
		StringBuilder sb = new StringBuilder();
		sb.append("命令: ");
		if (command.getArgs().size() != 0) sb.append(command.getArgs().get(0).replaceAll("小黑", "邓恩"));
		sb.append(" 有误或不存在\n")
				.append("请使用 '/帮助' 或 '/help' 查看帮助");
		return new SimplePromise<String>(deferred -> {
			deferred.resolve(sb.toString());
		}).then(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		});
	}
}
