package org.qqbot.core;

import org.junit.jupiter.api.Test;
import org.qqbot.entity.Command;
import org.qqbot.utils.MybatisUtil;

public class TestPermission {

  @Test
  public void test() {
    MybatisUtil.init();
    CommandInvoker invoker = new CommandPermission();
    Command command = new Command();
    command.addArgs("disable").addArgs("[mirai:asd").addArgs("掷骰子");
    invoker.invoke(null, command);
  }
}
