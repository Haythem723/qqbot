package org.qqbot.core;

import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.message.data.Voice;
import net.mamoe.mirai.utils.ExternalResource;
import org.jdeferred2.Promise;
import org.qqbot.constant.CommandType;
import org.qqbot.constant.ConstantMenu;
import org.qqbot.constant.ConstantVoice;
import org.qqbot.entity.Command;
import org.qqbot.entity.VoiceInfoItem;
import org.qqbot.mapper.MeaVoiceMapper;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.qqbot.constant.ConstantVoice.MAX_MEA_BUTTON_COUNT;

public class CommandMeaVoice implements CommandInvoker {
	@Override
	public Promise invoke(MessageEvent event, Command command) {
		if (!(event instanceof GroupMessageEvent)) {
			MiraiMain.getInstance().quickReply(event, "该功能仅供群组使用");
			return null;
		}
		ArrayList<String> args = command.getArgs();
		// 随机播放
		if (args.size() != 0) {
			return new CommandHelp().invoke(event, command.setType(CommandType.COMMAND_HELP).resetAndAddArgs(ConstantMenu.COMMAND_MEA_BUTTON));
		}
		int res = new Random().nextInt(MAX_MEA_BUTTON_COUNT) + 1;
		GroupMessageEvent finalEvent = (GroupMessageEvent) event;
		return new SimplePromise<SingleMessage>(deferred -> {
			VoiceInfoItem voiceInfo = MybatisUtil.getInstance().getSingleData(MeaVoiceMapper.class, VoiceInfoItem.class, "getVoiceInfo", String.valueOf(res));
			if (voiceInfo == null) {
				deferred.reject(new PlainText(CommonUtil.getCommandFailInfo(command, "文件信息获取失败, voiceId: " + res)));
				return;
			}
			try {
				ExternalResource externalResource;
				File resourceFile = FileUtil.getInstance().getVoiceResourceFile(voiceInfo.getFileName(), ConstantVoice.MEA_VOICE_FOLDER_NAME);
				if (!resourceFile.exists()) {
					externalResource = Mirai.getInstance().getFileCacheStrategy().newCache(new HttpUtil().getMeaInputStream(voiceInfo.getUrl()), voiceInfo.getFileName());
				} else {
					externalResource = Mirai.getInstance().getFileCacheStrategy().newCache(new FileInputStream(resourceFile), voiceInfo.getFileName());
				}
				Voice voice = ExternalResource.Companion.uploadAsVoice(externalResource, finalEvent.getGroup());
				deferred.resolve(voice);
			} catch (IOException e) {
				deferred.reject(new PlainText(CommonUtil.getCommandFailInfo(command, e) + "voiceId: " + res));
				e.printStackTrace();
			}
		}).then(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		}).fail(result -> {
			MiraiMain.getInstance().quickReply(event, result);
		});
	}
}
