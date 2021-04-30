package org.qqbot.core;

import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import org.jdeferred2.Promise;
import org.qqbot.constant.CommandType;
import org.qqbot.constant.ConstantMenu;
import org.qqbot.constant.ConstantVoice;
import org.qqbot.entity.Command;
import org.qqbot.entity.SaucenaoDataItem;
import org.qqbot.entity.SaucenaoHeaderItem;
import org.qqbot.entity.SaucenaoResult;
import org.qqbot.function.Saucenao;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.FileUtil;
import org.qqbot.utils.SimplePromise;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.qqbot.constant.ConstantImage.FILE_NAME_HNG;
import static org.qqbot.constant.ConstantSaucenao.DBIndex;
import static org.qqbot.constant.ConstantSaucenao.bandDBIndex;

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
			if (bandDBIndex.containsKey(index_id)) {
				File resourceFile = FileUtil.getInstance().getImageResourceFile(FILE_NAME_HNG);
				Image hngImage = ExternalResource.Companion.uploadAsImage(resourceFile, event.getSubject());
				MessageChain chain;
				if (hngImage == null) {
					chain = builder.append("H是禁止的").build();
				} else {
					chain = builder.append(hngImage).build();
				}
				deferred.reject(chain);
				return;
			}
			SaucenaoDataItem data = imageResult.getData();
			if (!(index_id == 5 || index_id == 21)) {
				data.setDefault(true);
				data.setPixiv(false);
				data.setAniDB_id(false);
			}
			boolean pixiv = data.isPixiv();
			boolean anidb = data.isAniDB();
			if(!(pixiv || anidb)){
				MessageChain build = builder.append(image)
						.append("\n相似度: ")
						.append(header.getSimilarity())
						.append("\n来源: ")
						.append(DBIndex.get(index_id) == null ? "未知" : DBIndex.get(index_id))
						.append("\nurl: ")
						.append(data.getExt_urls().get(0))
						.build();
				//TODO: 这里写不写URL呢？ 如果引用不了原图的话这里肯定得给URL
				//TODO: 那就写啊23333
				deferred.resolve(build);
				return;
			}
			MessageChain build = builder.append(image)
					.append("\n相似度: ")
					.append(header.getSimilarity())
					.append("\n来源: ")
					.append(pixiv ? "pixiv" : "anidb")
					.append("\n标题: ")
					.append(data.getTitle())
					.append("\n")
					.append(pixiv ? "pixiv_id: " : "anidb_id: ")
					.append(String.valueOf(pixiv ? data.getPixiv_id() : data.getAnidb_aid()))
					.append("\nurl: ")
					.append(data.getExt_urls().get(0))
					.build();
			deferred.resolve(build);
		}, result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}, result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}).me;
	}
}
