package org.qqbot.core;

import org.qqbot.constant.CommandType;
import org.qqbot.entity.Command;
import org.qqbot.entity.HelpInfoItem;
import org.qqbot.entity.HelpListItem;
import org.qqbot.mapper.HelpMapper;
import org.qqbot.mirai.MiraiMain;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Promise;
import org.qqbot.utils.CommonUtil;
import org.qqbot.utils.MybatisUtil;
import org.qqbot.utils.SimplePromise;

import java.util.ArrayList;
import java.util.List;

import static org.qqbot.constant.ConstantMenu.MAX_COMMAND_INDEX;

/**
 * 处理帮助指令
 *
 * @author diyigemt
 */
public class CommandHelp implements CommandInvoker {
	@Override
	public Promise<String, String, String> invoke(MessageEvent event, Command command) {
		StringBuilder sb = new StringBuilder().append("可用命令:\n");
		ArrayList<String> args = command.getArgs();
		if (args == null || args.size() == 0) {
			sb.append("使用 /帮助 指令序号 可以查看详细用法\n可用命令:\n");
			return new SimplePromise<String>(deferred -> {
				// 获取帮助列表
				List<HelpListItem> helpList = MybatisUtil.getInstance().getListData(HelpMapper.class, HelpListItem.class, "getHelpList");
				if (helpList == null || helpList.size() == 0) {
					deferred.reject(CommonUtil.getCommandFailInfo(command, "帮助列表获取失败"));
					return;
				}
				int index = 1;
				for (HelpListItem item : helpList) {
					sb.append(index)
							.append(".")
							.append(item.toString())
							.append("\n");
					index++;
				}
				deferred.resolve(sb.toString());
			}).then(result -> {
				MiraiMain.getInstance().quickReply(event, result);
			}).fail(result -> {
				MiraiMain.getInstance().quickReply(event, result);
			});
		}
		Integer integer = CommonUtil.parseInt(args.get(0));
		if (integer == null) return new CommandNull().invoke(event, command.resetAndAddArgs(args.get(0)));
		if (integer > MAX_COMMAND_INDEX || integer < 1)
			return new CommandNull().invoke(event, command.resetAndAddArgs(integer.toString()));
		// 获取具体帮助
		return new SimplePromise<String>(deferred -> {
			List<HelpInfoItem> helpInfo = MybatisUtil.getInstance().getListData(HelpMapper.class, HelpInfoItem.class, "getHelpInfo", args.get(0));
			if (helpInfo == null || helpInfo.size() == 0) {
				deferred.reject(CommonUtil.getCommandFailInfo(command, "帮助信息不存在或获取失败"));
				return;
			}
			int index = 1;
			for (HelpInfoItem item : helpInfo) {
				String tmp = (index++) + item.toString();
				sb.append(tmp)
						.append("\n");
			}
			deferred.resolve(sb.toString());
		}).then(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}).fail(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		});
	}
}
