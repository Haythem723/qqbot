package com.qqbot.listener;

import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.annotation.OnPrivate;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;

@Beans
public class TestListener {
  /**
   * 监听私聊消息并复读。
   */
  @OnPrivate
  public void privateMsg(PrivateMsg privateMsg, MsgSender sender) {
    // MsgSender中存在三大送信器，以及非常多的重载方法。
    sender.SENDER.sendPrivateMsg(privateMsg, privateMsg.getMsgContent());
  }

  /**
   * 监听消息开头为 "mua" 或者 "hi" 的消息并复读。
   */
  @OnGroup
  @Filter(value = "mua", matchType = MatchType.STARTS_WITH)
  @Filter(value = "hi", matchType = MatchType.STARTS_WITH)
  public void groupMsg(GroupMsg groupMsg, MsgSender sender) {
    sender.SENDER.sendGroupMsg(groupMsg, groupMsg.getMsgContent());
  }
}
