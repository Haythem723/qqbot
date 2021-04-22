package com.qqbot.listener;

import catcode.CatCodeUtil;
import catcode.CodeTemplate;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;

//@Beans
public class MeaVoiceListener {

  @OnGroup
  @Filter(value = "/咩", matchType = MatchType.CONTAINS)
  public void groupMsg(GroupMsg groupMsg, MsgSender sender) {
    System.out.println(groupMsg);
    String cat = "[CAT:voice,file=https://meamea.moe/voices/01-1.mp3]";
    sender.SENDER.sendGroupMsg(groupMsg, cat);
  }
}
