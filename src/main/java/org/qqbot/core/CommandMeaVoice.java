package org.qqbot.core;

import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.message.data.Voice;
import net.mamoe.mirai.utils.ExternalResource;
import org.jdeferred2.Promise;
import org.qqbot.Plugin;
import org.qqbot.constant.ConstantVoice;
import org.qqbot.entity.Command;
import org.qqbot.entity.VoiceInfoItem;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.mybatis.ImpMeaVoiceMapper;
import org.qqbot.utils.CommonUtil;
import org.qqbot.utils.FileUtil;
import org.qqbot.utils.HttpUtil;
import org.qqbot.utils.SimplePromise;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
		if (args.size() == 0) {
			int res = new Random().nextInt(MAX_MEA_BUTTON_COUNT) + 1;
			GroupMessageEvent finalEvent = (GroupMessageEvent) event;
			return new SimplePromise<SingleMessage>(deferred -> {
				VoiceInfoItem voiceInfo = new ImpMeaVoiceMapper().getVoiceInfo(String.valueOf(res));
				if (voiceInfo == null) {
					deferred.reject(new PlainText(CommonUtil.getCommandFailInfo(command) + "voiceId: " + res));
					return;
				}
				try {
					File resourceFile = FileUtil.getVoiceResourceFile(voiceInfo.getFileName(), ConstantVoice.MEA_VOICE_FOLDER_NAME);
//					ExternalResource externalResource = Mirai.getInstance().getFileCacheStrategy().newCache(new HttpUtil().getInputStream(voiceInfo.getUrl()), voiceInfo.getFileName());
					ExternalResource externalResource = Mirai.getInstance().getFileCacheStrategy().newCache(new FileInputStream(resourceFile), voiceInfo.getFileName());
					Voice voice = ExternalResource.Companion.uploadAsVoice(externalResource, finalEvent.getGroup());
					deferred.resolve(voice);
				} catch (IOException e) {
					deferred.reject(new PlainText(CommonUtil.getCommandFailInfo(command) + "voiceId: " + res + " 在上传时, exception class:" + e.getClass()));
					e.printStackTrace();
				}
			}).then(result -> {
				MiraiMain.getInstance().quickReply(event, result);
			}).fail(result -> {
				MiraiMain.getInstance().quickReply(event, result);
			});
		}
		return null;
	}
}
