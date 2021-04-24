package org.qqbot;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.qqbot.listener.GroupListener;

public final class Main extends JavaPlugin {
	public static final Main INSTANCE = new Main();

	private Main() {
		super(new JvmPluginDescriptionBuilder("org.qqbot.qqbot", "1.0")
				.author("diyigemt HayThem")
				.build());
	}

	@Override
	public void onEnable() {
		getLogger().info("Plugin loaded!");
		// 指定在botEvent 且 bot的id(qq号)为某一定值时 向该频道广播事件 触发监听器
		EventChannel<Event> channel = GlobalEventChannel.INSTANCE.filter(event -> event instanceof BotEvent && ((BotEvent) event).getBot().getId() == 2492921801L);
		channel.subscribeAlways(GroupMessageEvent.class, new GroupListener());
	}
}