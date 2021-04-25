package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Promise;
import org.qqbot.entity.Command;

public class CommandDice implements CommandInvoker{
	@Override
	public Promise invoke(MessageEvent event, Command command) {
		return null;
	}
}
