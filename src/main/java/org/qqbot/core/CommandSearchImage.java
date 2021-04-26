package org.qqbot.core;

import net.mamoe.mirai.IMirai;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;
import org.jdeferred2.Promise;
import org.qqbot.entity.Command;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.SimplePromise;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandSearchImage implements CommandInvoker {
	@Override
	public Promise invoke(MessageEvent event, Command command) {
		// TODO
		MessageChain message = event.getMessage();
		List<SingleMessage> collect = message.stream().filter(singleMessage -> singleMessage instanceof Image).collect(Collectors.toList());
		String s = "暂未开放";
		if (!collect.isEmpty()) {
			Image image = (Image) collect.get(0);
			s = Mirai.getInstance().queryImageUrl(event.getBot(), image);
		}
		String finalS = s;
		return new SimplePromise<String>(deferred -> {
			deferred.resolve(finalS);
		}).then(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		});
	}
}
