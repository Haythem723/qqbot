package org.qqbot;

import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.junit.jupiter.api.Test;
import org.qqbot.entity.Command;
import org.qqbot.entity.PermissionItem;
import org.qqbot.mapper.PermissionMapper;
import org.qqbot.utils.CommonUtil;
import org.qqbot.utils.MybatisUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;

public class TestSimple {

  @Test
  public void testSet() {
    Set<Object> objects = new HashSet<Object>();
    objects.addAll(Arrays.asList("123123", "12312312"));
    System.out.println(objects.toString());
  }

  @Test
  public void testPermissionMapper() {
    PermissionItem item = new PermissionItem(1355247243L, "2");
    item = MybatisUtil.getInstance().getSingleData(PermissionMapper.class, PermissionItem.class, "getPermission", item);

  }

  @Test
  public void testMessageChain() {
    MessageChainBuilder builder = new MessageChainBuilder();
    builder.append(new At(1231231231L))
        .append("/config permit add ")
        .append(new At(12312312333L))
        .append(" 2");
    MessageChain build = builder.build();
    String s = build.serializeToMiraiCode();
    Command command = CommonUtil.parseCommandAndArgs(s);
  }

  @Test
  public void testImp() {
    A a = new AA();
    A b = new BB();
    System.out.println(a.getClass().toString());
    System.out.println(b.getClass().toString());
  }

  interface A {
    void say();
  }

  @Target({ElementType.METHOD, ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @interface B {

  }

  class AA implements A {

    @Override
    @B
    public void say() {
      System.out.println("AA");
    }
  }

  class BB implements A {

    @Override
    @B
    public void say() {
      System.out.println("BB");
    }
  }
}
