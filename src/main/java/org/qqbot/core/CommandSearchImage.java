package org.qqbot.core;

import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import org.jdeferred2.Promise;
import org.qqbot.constant.CommandType;
import org.qqbot.constant.ConstantMenu;
import org.qqbot.constant.ConstantSetting;
import org.qqbot.constant.ConstantVoice;
import org.qqbot.entity.Command;
import org.qqbot.entity.SaucenaoDataItem;
import org.qqbot.entity.SaucenaoHeaderItem;
import org.qqbot.entity.SaucenaoResult;
import org.qqbot.function.Saucenao;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.FileUtil;
import org.qqbot.utils.HttpUtil;
import org.qqbot.utils.SettingUtil;
import org.qqbot.utils.SimplePromise;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
			return new CommandHelp().invoke(event, command.setType(CommandType.COMMAND_SEARCH_IMAGE).setHelpVirtualId(CommandType.COMMAND_SEARCH_IMAGE.getIndex()));
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
			// 获取r18设置
			boolean r18 = SettingUtil.getInstance().getBooleanValue(ConstantSetting.SETTING_ALLOW_R18, false);
			if (bandDBIndex.containsKey(index_id) && !r18) {
				File resourceFile = FileUtil.getInstance().getImageResourceFile(FILE_NAME_HNG);
				// 获取禁止r18图片
				Image hngImage = ExternalResource.Companion.uploadAsImage(resourceFile, event.getSubject());
				MessageChain chain;
				if (hngImage == null) {
					// 如果没获取到 回复文字
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
				MessageChain chain;
				builder.append(image)
						.append("\n相似度: ")
						.append(header.getSimilarity());
				if (data.isH()) {
					builder.append("\nen_name: ")
							.append(data.getEng_name())
							.append("\njp_name: ")
							.append(data.getJp_name());
				} else {
					builder.append("\n来源: ")
							.append(DBIndex.get(index_id) == null ? "未知" : DBIndex.get(index_id))
							.append("\nurl: ")
							.append(data.getExt_urls().get(0));
				}
				chain = builder.build();
				//TODO: 这里写不写URL呢？ 如果引用不了原图的话这里肯定得给URL
				//TODO: 那就写啊23333
				deferred.resolve(chain);
				return;
			}
			//如果来源与pixiv 尝试通过pixiv.cat反代获取原图
			if (pixiv) {
				String imageSource = Saucenao.constructSourceURL(imageResult);
				if (imageSource != null) {
					image = ExternalResource.Companion.uploadAsImage(new HttpUtil().getInputStream(imageSource), event.getSubject());
					builder.append("已获取原图:\n");
				}
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
