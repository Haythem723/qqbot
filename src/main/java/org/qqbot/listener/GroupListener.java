package org.qqbot.listener;

import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.qqbot.core.CommandHelp;

import java.util.function.Consumer;

public class GroupListener implements Consumer<GroupMessageEvent> {
	@Override
	public void accept(GroupMessageEvent groupMessageEvent) {
		String content = groupMessageEvent.getMessage().serializeToMiraiCode();
		if (content.contains("[mirai:at:2492921801]")) {
			CommandHelp help = new CommandHelp();

			help.invoke(groupMessageEvent);
		}
	}
}
