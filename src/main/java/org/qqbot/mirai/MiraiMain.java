package org.qqbot.mirai;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class MiraiMain {
	public static final MiraiMain INSTANCE = new MiraiMain();
	public static MiraiMain getInstance() {
		return INSTANCE;
	}

	public void quickReply(MessageEvent event, String msg) {
		long senderId = event.getSender().getId();
		MessageChain chain = null;
		if (event instanceof GroupMessageEvent) {
			chain = new MessageChainBuilder().append(new At(senderId)).append(msg).build();
		}
		if (event instanceof FriendMessageEvent) {
			chain = new MessageChainBuilder().append(msg).build();
		}
		event.getSubject().sendMessage(chain);
	}
}
