package org.qqbot.core;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.qqbot.mirai.MiraiMain;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Deferred;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.qqbot.constant.ConstantMenu;
import org.qqbot.utils.CommonUtil;

/**
 * @author diyigemt
 */
public class CommandHelp implements CommandInvoker {
	@Override
	public Promise invoke(MessageEvent event, String... args) {
		StringBuilder sb = new StringBuilder();
		sb.append("可用命令:\n");
		if (args.length == 0) {
			Deferred<String, String, String> deferred = new DeferredObject<String, String, String>();
			Promise<String, String, String> promise = deferred.promise();
			promise.then(res -> {
				MiraiMain.getInstance().quickReply(event, res);
			});
			// Do something
			deferred.resolve("hahaha");
			return promise;
		}
		Integer integer = CommonUtil.parseInt(args[0]);
		if (integer == null) return this.invoke(event, ConstantMenu.MENU_HELP);
		Deferred<String, String, String> deferred = new DeferredObject<String, String, String>();
		Promise<String, String, String> promise = deferred.promise();
		promise.then(res -> {
			MiraiMain.getInstance().quickReply(event, res);
		});
		// Do something
		deferred.resolve("hahaha");
		return promise;
	}
}
