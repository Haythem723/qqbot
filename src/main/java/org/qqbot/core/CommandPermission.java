package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.SingleMessage;
import org.jdeferred2.Promise;
import org.qqbot.entity.Command;
import org.qqbot.entity.HelpListItem;
import org.qqbot.mapper.HelpMapper;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.MybatisUtil;
import org.qqbot.utils.PermissionUtil;
import org.qqbot.utils.SimplePromise;
import org.qqbot.constant.ConstantSetting.PermitOperate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandPermission implements CommandInvoker {
  @Override
  public Promise invoke(MessageEvent event, Command command) {
    ArrayList<String> args = command.getArgs();
    if (args == null || args.size() == 0) return handleError(event, "没有参数\n可用: set remove disable permit");
    if (args.size() < 3) return handleError(event, "ArgumentNotMatchException \nexpected: 3 or more, got: " + args.size());
    String targetOperate = args.get(0);
    String commandName1 = args.get(1);
    String commandName2 = args.get(2);
    String commandName = commandName1.contains("[mirai:") ? commandName2 : commandName1;
    StringBuilder sb = new StringBuilder();
    HelpListItem item = checkCommandExist(commandName);
    if (item == null) {
      sb.append("指令: ").append(commandName).append(" 不存在!");
      return handleError(event, sb.toString());
    }
    List<SingleMessage> collect = event.getMessage().stream().filter(message -> message instanceof At && ((At) message).getTarget() != event.getBot().getId()).collect(Collectors.toList());
    if (collect.isEmpty()){
      collect.add(new At(event.getSender().getId()));
    }
    PermitOperate operate = PermitOperate.getPermitOperateByString(targetOperate);
    switch (operate) {
      case OPERATE_ADD: {
        return new SimplePromise<String>(deferred -> {
          sb.append("对群友: ");
          for (SingleMessage message : collect) {
            At at = (At) message;
            PermissionUtil.getInstance().addPermissionItem(at.getTarget(), item.getIntId());
            sb.append(at.getTarget())
                .append(", ");
          }
          sb.append("添加权限: ")
              .append(item.getCommand())
              .append("成功");
          deferred.resolve(sb.toString());
        }).then(result -> {
          MiraiMain.getInstance().quickReply(event, result);
        });
      }
      case OPERATE_REMOVE: {
        return new SimplePromise<String>(deferred -> {
          sb.append("对群友: ");
          for (SingleMessage message : collect) {
            At at = (At) message;
            PermissionUtil.getInstance().removePermissionItem(at.getTarget(), item.getIntId());
            sb.append(at.getTarget())
                .append(", ");
          }
          sb.append("移除权限: ")
              .append(item.getCommand())
              .append("成功");
          deferred.resolve(sb.toString());
        }).then(result -> {
          MiraiMain.getInstance().quickReply(event, result);
        });
      }
      case OPERATE_ENABLE: {
        return new SimplePromise<String>(deferred -> {
          sb.append("对群友: ");
          for (SingleMessage message : collect) {
            At at = (At) message;
            PermissionUtil.getInstance().enablePermissionItem(at.getTarget(), item.getIntId());
            sb.append(at.getTarget())
                .append(", ");
          }
          sb.append("启用权限: ")
              .append(item.getCommand())
              .append("成功");
          deferred.resolve(sb.toString());
        }).then(result -> {
          MiraiMain.getInstance().quickReply(event, result);
        });
      }
      case OPERATE_DISABLE: {
        return new SimplePromise<String>(deferred -> {
          sb.append("对群友: ");
          for (SingleMessage message : collect) {
            At at = (At) message;
            PermissionUtil.getInstance().disablePermissionItem(at.getTarget(), item.getIntId());
            sb.append(at.getTarget())
                .append(", ");
          }
          sb.append("禁用权限: ")
              .append(item.getCommand())
              .append("成功");
          deferred.resolve(sb.toString());
        }).then(result -> {
          MiraiMain.getInstance().quickReply(event, result);
        });
      }
      case OPERATE_NULL: {
        sb.append("指令不存在");
        break;
      }
      default: {
        sb.append("操作异常");
      }
    }
    return new SimplePromise<String>(deferred -> {
      deferred.resolve(sb.toString());
    }).then(result -> {
      MiraiMain.getInstance().quickReply(event, result);
    });
  }

  private Promise handleError(MessageEvent event, String msg) {
    return new SimplePromise<String>(deferred -> {
      deferred.resolve(msg);
    }, result -> {
      MiraiMain.getInstance().quickReply(event, result);
    }).me();
  }

  private HelpListItem checkCommandExist(String commandName) {
    return MybatisUtil.getInstance().getSingleData(HelpMapper.class, HelpListItem.class, "getHelpListItem", commandName);
  }
}
