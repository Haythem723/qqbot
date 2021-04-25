package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Deferred;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.qqbot.constant.CommandType;
import org.qqbot.entity.Command;
import org.qqbot.function.Dice;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.SimplePromise;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理骰子指令
 * @author diyigemt HayThem
 */
public class CommandDice implements CommandInvoker {

	private static final Pattern dicePattern = Pattern.compile("^([0-9]+)[dD]([0-9]+)$");

	@Override
	public Promise<String, String, String> invoke(MessageEvent event, Command command) {
		ArrayList<String> args = command.getArgs();
		if (args == null || args.size() != 1) {
			return this.handleErrorArgs(event, command);
		}
		Matcher matcher = dicePattern.matcher(args.get(0));
		if (!matcher.find()) {
			return this.handleErrorArgs(event, command);
		}
		String roll = Dice.getRoll(args.get(0));
		return new SimplePromise<String>(deferred -> {
			deferred.resolve(roll);
		}).then(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		});
	}

	private Promise<String, String, String> handleErrorArgs(MessageEvent event, Command command) {
		return new CommandHelp().invoke(event, command.resetAndAddArgs(CommandType.COMMAND_DICE.getIndex()));
	}
}
