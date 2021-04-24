package org.qqbot.core;


import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Promise;
import org.qqbot.entity.Command;

/**
 * @author HayThem diyiegmt
 * @time 2021-04-24 09:08:55
 */
public interface CommandInvoker {
	Promise invoke(MessageEvent event, Command command);
}
