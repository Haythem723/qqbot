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
import org.qqbot.utils.CommonUtil;
import org.qqbot.utils.MybatisUtil;

import java.util.List;

/**
 * 处理帮助指令
 * @author diyigemt
 */
public class CommandHelp implements CommandInvoker {
	@Override
	public Promise<String, String, String> invoke(MessageEvent event, Command command) {
		StringBuilder sb = new StringBuilder().append("\n可用命令:\n");
		String[] args = command.getArgs();
		if (args == null || args.length == 0) {
			sb.append("使用 /帮助 指令序号 可以查看详细用法\n可用命令:\n");
			Deferred<String, String, String> deferred = new DeferredObject<String, String, String>();
			Promise<String, String, String> promise = deferred.promise();
			promise.then(res -> {
				MiraiMain.getInstance().quickReply(event, res);
			});
			// 获取帮助列表
			SqlSession sqlSession = MybatisUtil.getSqlSession();
			HelpMapper mapper = sqlSession.getMapper(HelpMapper.class);
			List<HelpListItem> helpList = mapper.getHelpList();
			sqlSession.close();
			for (HelpListItem item : helpList) {
				sb.append(item.toString())
						.append("\n");
			}
			deferred.resolve(sb.toString());
			return promise;
		}
		Integer integer = CommonUtil.parseInt(args[0]);
		if (integer == null) return this.invoke(event, command.setType(CommandType.COMMAND_HELP));
		Deferred<String, String, String> deferred = new DeferredObject<String, String, String>();
		Promise<String, String, String> promise = deferred.promise();
		promise.then(res -> {
			MiraiMain.getInstance().quickReply(event, res);
		});
		// 获取具体帮助
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		HelpMapper mapper = sqlSession.getMapper(HelpMapper.class);
		List<HelpInfoItem> helpInfo = mapper.getHelpInfo(args[0]);
		int index = 1;
		for (HelpInfoItem item : helpInfo) {
			String tmp = (index++) + item.toString();
			sb.append(tmp)
					.append("\n");
		}
		sqlSession.close();
		deferred.resolve(sb.toString());
		return promise;
	}
}
