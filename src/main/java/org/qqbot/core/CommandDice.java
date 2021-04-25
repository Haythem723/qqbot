package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Deferred;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.qqbot.constant.CommandType;
import org.qqbot.entity.Command;
import org.qqbot.function.Dice;
import org.qqbot.mirai.MiraiMain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理骰子指令
 * @author diyigemt HayThem
 */
public class CommandDice implements CommandInvoker{

	private static final Pattern dicePattern = Pattern.compile("^([0-9]+)[dD]([0-9]+)$");

	@Override
	public Promise<String, String, String> invoke(MessageEvent event, Command command) {
		String[] args = command.getArgs();
		if (args == null || args.length != 1) {
			return this.handleErrorArgs(event, command);
		}
		Matcher matcher = dicePattern.matcher(args[0]);
		if (!matcher.find()) {
			return this.handleErrorArgs(event, command);
		}
		String roll = Dice.getRoll(args[0]);
		Deferred<String, String, String> deferred = new DeferredObject<String, String, String>();
		Promise<String, String, String> promise = deferred.promise();
		promise.then(res -> {
			MiraiMain.getInstance().quickReply(event, res);;
		});
		deferred.resolve(roll);
		return promise;
	}

	private Promise<String, String, String> handleErrorArgs(MessageEvent event, Command command) {
		String[] args = new String[1];
		args[0] = CommandType.COMMAND_DICE.getIndex();
		return new CommandHelp().invoke(event, command.setArgs(args));
	}
}
