package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.message.data.Voice;
import org.jdeferred2.Promise;
import org.qqbot.entity.Command;
import org.qqbot.entity.VoiceInfoItem;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.mybatis.ImpMeaVoiceMapper;
import org.qqbot.utils.SimplePromise;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import static org.qqbot.constant.ConstantVoice.MAX_MEA_BUTTON_COUNT;

public class CommandMeaVoice implements CommandInvoker {
	@Override
	public Promise invoke(MessageEvent event, Command command) {
		ArrayList<String> args = command.getArgs();
		// 随机播放
		if (args.size() == 0) {
			int res = new Random().nextInt(MAX_MEA_BUTTON_COUNT) + 1;
			VoiceInfoItem voiceInfo = new ImpMeaVoiceMapper().getVoiceInfo(String.valueOf(res));
			if (voiceInfo != null) {
				Voice voice = new Voice(voiceInfo.getFileName(), "1231312".getBytes(StandardCharsets.UTF_8), 12313123L, 12312312, voiceInfo.getUrl());
				return new SimplePromise<SingleMessage>(result -> {
//					MiraiMain.getInstance().quickReply(event, result);
				}).resolve(voice);
			}
		}
		return null;
	}
}
