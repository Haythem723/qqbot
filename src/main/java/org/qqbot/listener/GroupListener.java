package org.qqbot.listener;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.qqbot.core.*;
import org.qqbot.entity.Command;
import org.qqbot.utils.CommonUtil;

import java.util.function.Consumer;

public class GroupListener implements Consumer<GroupMessageEvent> {
	@Override
	public void accept(GroupMessageEvent groupMessageEvent) {
		String content = groupMessageEvent.getMessage().serializeToMiraiCode();
		// 若消息没有@Bot则不做回应
		if (!content.contains("[mirai:at:1741557205]")) return;
		content = content.replace("[mirai:at:1741557205]", "").trim();
		Command command = CommonUtil.parseCommandAndArgs(content);
		CommandInvoker invoker;
		switch (command.getType()) {
			case COMMAND_SETTING: {
				invoker = new CommandSetting();
				break;
			}
			case COMMAND_HELP: {
				invoker = new CommandHelp();
				break;
			}
			case COMMAND_DICE: {
				invoker = new CommandDice();
				break;
			}
			case COMMAND_NEED: {
				invoker = new CommandNeed();
				break;
			}
			case COMMAND_MEA_BUTTON: {
				invoker = new CommandMeaVoice();
				break;
			}
			case COMMAND_MANA_BUTTON: {
				invoker = new CommandMana();
				break;
			}
			case COMMAND_GET_HAPPY:{
				invoker = new CommandJoke();
				break;
			}
			case COMMAND_SEARCH_IMAGE: {
				invoker = new CommandSearchImage();
				break;
			}
			default: {
				invoker = new CommandNull();
			}
		}
		invoker.invoke(groupMessageEvent, command);
	}
}
