package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;
import org.jdeferred2.Promise;
import org.qqbot.entity.Command;
import org.qqbot.entity.SensitiveItem;
import org.qqbot.function.Sensitive;
import org.qqbot.mapper.SensitiveMapper;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.FileUtil;
import org.qqbot.utils.MybatisUtil;
import org.qqbot.utils.SimplePromise;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.qqbot.constant.ConstantImage.FILE_NAME_SENSITIVE_GIF;

public class CommandNull implements CommandInvoker {
	@Override
	public Promise invoke(MessageEvent event, Command command) {
		AtomicBoolean Sensitive = new AtomicBoolean(false);
		StringBuilder sb = new StringBuilder();
		sb.append("命令: ");
		if (command.getArgs().size() != 0) {
			final String[] content = new String[1];
			content[0] = command.getArgs().get(0);
			List<SensitiveItem> sensitiveList = MybatisUtil.getInstance().getListData(SensitiveMapper.class, SensitiveItem.class, "getSensitiveList");
			sensitiveList.forEach(item -> {
				String s = content[0];
				s = new Sensitive().process(s);//首拼处理
				if (s.contains(item.getRootword())){
					Sensitive.set(true);
				}
			});
			sb.append(content[0]);
		}
		sb.append(" 有误或不存在\n")
				.append("请使用 '/帮助' 或 '/help' 查看帮助");


		return new SimplePromise<MessageChain>(deferred -> {
			MessageChain chain;
			MessageChainBuilder builder = new MessageChainBuilder();
			if(Sensitive.get()){
				File resourceFile = FileUtil.getInstance().getImageResourceFile(FILE_NAME_SENSITIVE_GIF);
				Image GIFImage = ExternalResource.Companion.uploadAsImage(resourceFile, event.getSubject());
				if (GIFImage == null) {
					chain = builder.append("不能再犹豫了，一定要出重拳").build();
				} else {
					chain = builder.append(GIFImage).build();
				}
				deferred.reject(chain);
				return;
			}
			else {
				chain = builder.append(sb.toString()).build();
				deferred.resolve(chain);
			}
		}).then(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		});
	}
}
