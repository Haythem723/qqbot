package org.qqbot.core;

import com.sun.xml.internal.bind.v2.TODO;
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
			int index_id = header.getIndex_id();
			SaucenaoDataItem data = imageResult.getData();
			if (index_id == 34) {
				data.setPixiv(true);
				data.setDefault(false);
			}
			else if (index_id == 21) {
				data.setAniDB_id(true);
				data.setDefault(false);
			}
			boolean pixiv = data.isPixiv();
			boolean anidb = data.isAniDB();
			if(pixiv || anidb){
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
			}
			else {
				MessageChain build = builder.append(image)
						.append("\n相似度:")
						.append(header.getSimilarity())
						.append("\n来源:")
						.append("其它来源")
						.build();
				//TODO: 这里写不写URL呢？ 如果引用不了原图的话这里肯定得给URL
				deferred.resolve(build);
			}
		}, result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}, result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}).me();
	}
}
