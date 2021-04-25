package org.qqbot.mirai;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

/**
 * 全局实例 用于回复消息
 * @author diyigemt
 */
public class MiraiMain {
	// 如果为开发模式 在国内可能会因为回复太快导致消息不显示 睡眠1s
	private static final boolean DEVELOPMENT = true;
	// 全局唯一实例
	public static final MiraiMain INSTANCE = new MiraiMain();

	/**
	 * 获取实例
	 * @return 全局实例
	 */
	public static MiraiMain getInstance() {
		return INSTANCE;
	}

	/**
	 * 快速回复消息
	 * @param event 消息事件
	 * @param msg 要回复的消息内容
	 */
	public void quickReply(MessageEvent event, String msg) {
		long senderId = event.getSender().getId();
		MessageChain chain = null;
		if (event instanceof GroupMessageEvent) {
			// 如果是群事件 回复时@本人
			chain = new MessageChainBuilder().append(new At(senderId)).append(msg).build();
		}
		if (event instanceof FriendMessageEvent) {
			chain = new MessageChainBuilder().append(msg).build();
		}
		// 在非部署模式下 猜测可能会由于回复太快导致消息无法显示 先等待一下
		if (DEVELOPMENT) {
			try {
				Thread.sleep(1500);
				event.getSubject().sendMessage(chain);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			event.getSubject().sendMessage(chain);
		}
	}
}
