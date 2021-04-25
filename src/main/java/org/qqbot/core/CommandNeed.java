package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Promise;
import org.qqbot.entity.Command;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.SimplePromise;

public class CommandNeed implements CommandInvoker {
	@Override
	public Promise invoke(MessageEvent event, Command command) {
		// TODO
		return new SimplePromise<String>(deferred -> {
			deferred.resolve("暂未开放");
		}).then(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		});
	}
}
