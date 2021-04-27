package org.qqbot.core;

import net.mamoe.mirai.IMirai;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import org.jdeferred2.Promise;
import org.qqbot.constant.CommandType;
import org.qqbot.constant.ConstantMenu;
import org.qqbot.entity.Command;
import org.qqbot.entity.SaucenaoDataItem;
import org.qqbot.entity.SaucenaoHeaderItem;
import org.qqbot.entity.SaucenaoResult;
import org.qqbot.function.Saucenao;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.SimplePromise;

import java.util.List;
import java.util.stream.Collectors;

public class CommandSearchImage implements CommandInvoker {
	@Override
	public Promise invoke(MessageEvent event, Command command) {
		// TODO
		MessageChain message = event.getMessage();
		List<SingleMessage> collect = message.stream().filter(singleMessage -> singleMessage instanceof Image).collect(Collectors.toList());
		if (collect.isEmpty()) {
			return new CommandHelp().invoke(event, command.setType(CommandType.COMMAND_SEARCH_IMAGE).resetAndAddArgs(ConstantMenu.COMMAND_SEARCH_IMAGE));
		}
		return new SimplePromise<MessageChain>(deferred -> {
			Image image = (Image) collect.get(0);
			String imageUrl = Mirai.getInstance().queryImageUrl(event.getBot(), image);
			SaucenaoResult imageResult = Saucenao.getResult(imageUrl);
			MessageChainBuilder builder = new MessageChainBuilder();
			if (imageResult.getStatus() != 0) {
				deferred.reject(builder.append(imageResult.getMsg()).build());
				return;
			}
			SaucenaoHeaderItem header = imageResult.getHeader();
			SaucenaoDataItem data = imageResult.getData();
			boolean pixiv = data.isPixiv();
			MessageChain build = builder.append(image)
					.append("\n相似度:")
					.append(header.getSimilarity())
					.append("\n来源:")
					.append(pixiv ? "pixiv" : "anidb")
					.append("\n标题: ")
					.append(data.getTitle())
					.append("\n" + (pixiv ? "pixiv_id:" : "anidb_id:"))
					.append(String.valueOf(pixiv ? data.getPixiv_id() : data.getAnidb_aid()))
					.build();
			deferred.resolve(build);
		}).then(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}).fail(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		});
	}
}
