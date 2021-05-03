package org.qqbot.utils;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.action.UserNudge;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.qqbot.core.CommandDice;
import org.qqbot.core.CommandInvoker;
import org.qqbot.core.CommandJoke;
import org.qqbot.core.CommandNull;
import org.qqbot.entity.Command;
import org.qqbot.function.Saucenao;

public class TestCommonUtil {
	@Test
	public void testParseCommand() {
		MybatisUtil.init();
		String content = "/1d5";
		Command command = CommonUtil.parseCommandAndArgs(content);
		CommandInvoker invoker = new CommandDice();
		invoker.invoke(new MessageEvent() {
			@NotNull
			@Override
			public Bot getBot() {
				return null;
			}

			@NotNull
			@Override
			public Contact getSubject() {
				return null;
			}

			@NotNull
			@Override
			public User getSender() {
				return null;
			}

			@NotNull
			@Override
			public String getSenderName() {
				return null;
			}

			@NotNull
			@Override
			public MessageChain getMessage() {
				return null;
			}

			@Override
			public int getTime() {
				return 0;
			}

			@Override
			public boolean isIntercepted() {
				return false;
			}

			@Override
			public void intercept() {

			}
		}, command);
	}

	@Test
	public void testEncoding() {
		System.out.println(System.getProperty("file.encoding"));
	}

	@Test
	public void testSplit() {
		String s = "aaaaaa";
		String[] split = s.split("\\[n]");
		System.out.println(split);
	}
}
