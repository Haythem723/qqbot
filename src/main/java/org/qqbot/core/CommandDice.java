package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Promise;
import org.qqbot.annotation.GroupPermission;
import org.qqbot.constant.CommandType;
import org.qqbot.entity.Command;
import org.qqbot.entity.DiceLogItem;
import org.qqbot.entity.DiceMessageItem;
import org.qqbot.entity.DiceResultItem;
import org.qqbot.function.Dice;
import org.qqbot.mapper.DiceMapper;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.MybatisUtil;
import org.qqbot.utils.SimplePromise;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理骰子指令
 * @author diyigemt HayThem
 */
public class CommandDice implements CommandInvoker {

	private static final Pattern dicePattern = Pattern.compile("^([0-9]+)[dD*]([0-9]+)$");

	@Override
	@GroupPermission(blocks = {"1355247243"})
	public Promise invoke(MessageEvent event, Command command) {
		ArrayList<String> args = command.getArgs();
		if (args == null || args.size() > 2) {
			return this.handleErrorArgs(event, command);
		}
		if (args.size() == 2 && args.get(1).equals("log")) {
			return handleLog(event);
		}
		Matcher matcher = dicePattern.matcher(args.get(0));
		if (!matcher.find()) {
			if (args.get(0).equals("log")) {
				return handleLog(event);
			}
			return this.handleErrorArgs(event, command);
		}
		return new SimplePromise<String>(deferred -> {
			String senderId = String.valueOf(event.getSender().getId());
			DiceResultItem resultItem = Dice.getRoll(args.get(0), event.getSenderName(), senderId);
			List<DiceMessageItem> messageItems = MybatisUtil.getInstance().getListData(DiceMapper.class, DiceMessageItem.class, "getDiceMessage", resultItem.getResultSum());
			if (messageItems.size() == 0) {
				deferred.resolve(resultItem.toString());
				return;
			}
			if (messageItems.size() == 1) {
				deferred.resolve(resultItem.setMessage(messageItems.get(0).getMessage()).toString());
				return;
			}
			final DiceMessageItem[] message = {messageItems.get(0)};
			messageItems.forEach(item -> {
				if (item.getId() == Long.parseLong(item.getReceiverId())) {
					message[0] = item;
				}
			});
			deferred.resolve(resultItem.setMessage(message[0].getMessage()).toString());
		}, result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}).me();
	}

	private Promise<String, String, String> handleErrorArgs(MessageEvent event, Command command) {
		return new CommandHelp().invoke(event, command.setHelpVirtualId(CommandType.COMMAND_DICE.getIndex()));
	}

	private Promise handleLog(MessageEvent event) {
		return new SimplePromise<List<DiceLogItem>>(deferred -> {
			List<DiceLogItem> logs = MybatisUtil.getInstance().getListData(DiceMapper.class, DiceLogItem.class, "getSenderDiceLog", String.valueOf(event.getSender().getId()));
			if (logs == null) {
				deferred.reject(null);
				return;
			}
			deferred.resolve(logs);
		}, result -> {
			StringBuilder sb = new StringBuilder();
			for (DiceLogItem item : result) {
				sb.append(item.toString())
						.append("\n");
			}
			MiraiMain.getInstance().quickReply(event, sb.toString());
		}, result -> {
			MiraiMain.getInstance().quickReply(event, "无");
		}).me();
	}
}
