package org.qqbot;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public final class Plugin extends JavaPlugin {
  public static final Plugin INSTANCE = new Plugin();

  private Plugin() {
    super(new JvmPluginDescriptionBuilder("org.qqbot.plugin", "1.0-SNAPSHOT")
        .name("QQBot")
        .info("Mirai robot")
        .author("diyigemt, Haythem")
        .build());
  }

  @Override
  public void onEnable() {
    getLogger().info("QQ robot Plugin loaded!");
    Listener listener = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, new GroupListener());
  }
}