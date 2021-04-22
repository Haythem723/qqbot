package com.qqbot;

import love.forte.simbot.annotation.SimbotApplication;
import love.forte.simbot.core.SimbotApp;
import love.forte.simbot.core.SimbotContext;

@SimbotApplication
public class Main {
  public static void main(String[] args) {
    final SimbotContext context = SimbotApp.run(Main.class, args);
  }
}
