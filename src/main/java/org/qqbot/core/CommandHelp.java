package org.qqbot.core;

import org.apache.ibatis.session.SqlSession;
import org.qqbot.constant.CommandType;
import org.qqbot.entity.Command;
import org.qqbot.entity.HelpInfoItem;
import org.qqbot.entity.HelpListItem;
import org.qqbot.mapper.HelpMapper;
import org.qqbot.mirai.MiraiMain;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Deferred;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.qqbot.mybatis.ImpHelpMapper;
import org.qqbot.utils.CommonUtil;
import org.qqbot.utils.MybatisUtil;
import org.qqbot.utils.SimplePromise;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理帮助指令
 * @author diyigemt
 */
public class CommandHelp implements CommandInvoker {
	@Override
	public Promise<String, String, String> invoke(MessageEvent event, Command command) {
		ImpHelpMapper impHelpMapper = new ImpHelpMapper();
		StringBuilder sb = new StringBuilder().append("可用命令:\n");
		ArrayList<String> args = command.getArgs();
		if (args == null || args.size() == 0) {
			sb.append("使用 /帮助 指令序号 可以查看详细用法\n可用命令:\n");
			// 获取帮助列表
			List<HelpListItem> helpList = impHelpMapper.getHelpList();
			for (HelpListItem item : helpList) {
				sb.append(item.toString())
						.append("\n");
			}
			return new SimplePromise<String>(result -> {
				MiraiMain.getInstance().quickReply(event, result);
			}).resolve(sb.toString());
		}
		Integer integer = CommonUtil.parseInt(args.get(0));
		if (integer == null) return this.invoke(event, command.setType(CommandType.COMMAND_HELP));
		// 获取具体帮助
		List<HelpInfoItem> helpInfo = impHelpMapper.getHelpInfo(args.get(0));
		int index = 1;
		for (HelpInfoItem item : helpInfo) {
			String tmp = (index++) + item.toString();
			sb.append(tmp)
					.append("\n");
		}
		return new SimplePromise<String>(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}).resolve(sb.toString());
	}
}
