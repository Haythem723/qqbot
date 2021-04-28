package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Deferred;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.qqbot.constant.CommandType;
import org.qqbot.entity.Command;
import org.qqbot.entity.DiceLogItem;
import org.qqbot.function.Dice;
import org.qqbot.mapper.DiceLogMapper;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.MybatisUtil;
import org.qqbot.utils.SimplePromise;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理骰子指令
 * @author diyigemt HayThem
 */
public class CommandDice implements CommandInvoker {

	private static final Pattern dicePattern = Pattern.compile("^([0-9]+)[dD]([0-9]+)$");

	@Override
	public Promise invoke(MessageEvent event, Command command) {
		ArrayList<String> args = command.getArgs();
		if (args == null || args.size() > 2) {
			return this.handleErrorArgs(event, command);
		}
		if (args.size() == 2 && args.get(1).equals("log")) {
			List<DiceLogItem> logs = MybatisUtil.getInstance().getListData(DiceLogMapper.class, DiceLogItem.class, "getSenderDiceLog", "1355247243");
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
			String roll = Dice.getRoll(args.get(0), event.getSenderName(), String.valueOf(event.getSender().getId()));
			deferred.resolve(roll);
		}, result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}).me();
	}

	private Promise<String, String, String> handleErrorArgs(MessageEvent event, Command command) {
		return new CommandHelp().invoke(event, command.resetAndAddArgs(CommandType.COMMAND_DICE.getIndex()));
	}

	private Promise handleLog(MessageEvent event) {
		return new SimplePromise<List<DiceLogItem>>(deferred -> {
			List<DiceLogItem> logs = MybatisUtil.getInstance().getListData(DiceLogMapper.class, DiceLogItem.class, "getSenderDiceLog", "1355247243");
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
