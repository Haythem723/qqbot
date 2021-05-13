package org.qqbot;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import org.qqbot.listener.GroupListener;
import org.qqbot.utils.FileUtil;
import org.qqbot.utils.GlobalLoader;
import org.qqbot.utils.MybatisUtil;
import org.qqbot.utils.SettingUtil;

import java.io.File;

public final class Plugin extends JavaPlugin {
	public static final Plugin INSTANCE = new Plugin();
	public static final MiraiLogger logger = INSTANCE.getLogger();

	private Plugin() {
		super(new JvmPluginDescriptionBuilder("org.qqbot", "1.2.7")
				.author("diyigemt HayThem")
				.name("qq-bot")
				.build());
	}

	@Override
	public void onEnable() {
//		Listener<GroupMessageEvent> listener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, new GroupListener());
		getLogger().info("Plugin text loaded!");
		//指定在botEvent 且 bot的id(qq号)为某一定值时 向该频道广播事件 触发监听器
		EventChannel<Event> channel = GlobalEventChannel.INSTANCE.filter(event -> event instanceof BotEvent && ((BotEvent) event).getBot().getId() == 1741557205L);
		channel.subscribeAlways(GroupMessageEvent.class, new GroupListener());
		GlobalLoader.init();
//		FileUtil.init();
//		MybatisUtil.init();
//		SettingUtil.init();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}