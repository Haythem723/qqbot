package org.qqbot.core;

import net.mamoe.mirai.event.events.MessageEvent;
import org.jdeferred2.Promise;
import org.qqbot.annotation.GroupPermission;
import org.qqbot.constant.ConstantSetting.SettingOperate;
import org.qqbot.entity.Command;
import org.qqbot.entity.SettingItem;
import org.qqbot.mapper.SettingMapper;
import org.qqbot.mirai.MiraiMain;
import org.qqbot.utils.MybatisUtil;
import org.qqbot.utils.SettingUtil;
import org.qqbot.utils.SimplePromise;

import java.util.ArrayList;

public class CommandSetting implements CommandInvoker {
  @Override
  @GroupPermission(allows = {"1355247243", "1328343252"})
  public Promise invoke(MessageEvent event, Command command) {
    ArrayList<String> args = command.getArgs();
    if (args == null || args.size() == 0) {
      return handleError(event, "没有参数\n可用: set enable disable permit");
    }
    String targetOperate = args.get(0);
    String settingKey = args.get(1);
    StringBuilder sb = new StringBuilder();
    if (!targetOperate.equals("permit") && checkSettingExist(settingKey)) {
      sb.append(settingKey).append(" 不存在!");
      return handleError(event, sb.toString());
    }
    SettingOperate operate = SettingOperate.getOperateByString(targetOperate);
    switch (operate) {
      case OPERATE_SET: {
      	if (args.size() < 3) {
      		sb.append("ArgumentNotMatchException \nexpected: 3 or more, got: ")
							.append(args.size());
          break;
        }
      	String settingValue = args.get(2);
        if (SettingUtil.getInstance().set(settingKey, settingValue)) {
          handleInfo(sb, settingKey, settingValue);
          SettingUtil.getInstance().set(settingKey, settingValue);
        } else {
          sb.append("操作失败");
        }
        break;
      }
      case OPERATE_ENABLE: {
        if (args.size() < 2) {
          sb.append("ArgumentNotMatchException \nexpected: 2 or more, got: ")
              .append(args.size());
          break;
        }
        if (SettingUtil.getInstance().setBooleanValue(settingKey, true)) {
          handleEnableInfo(sb, settingKey);
        } else {
          sb.append("操作失败");
        }
        break;
      }
      case OPERATE_DISABLE: {
        if (args.size() < 2) {
          sb.append("ArgumentNotMatchException \nexpected: 2 or more, got: ")
              .append(args.size());
          break;
        }
        if (SettingUtil.getInstance().setBooleanValue(settingKey, false)) {
          handleDisableInfo(sb, settingKey);
        } else {
          sb.append("操作失败");
        }
        break;
      }
      case OPERATE_PERMIT: {
        args.remove("permit");
        return new CommandPermission().invoke(event, command);
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

  private StringBuilder handleEnableInfo(StringBuilder sb, String settingKey) {
    return handleInfo(sb, settingKey, "enable");
  }

  private StringBuilder handleDisableInfo(StringBuilder sb, String settingKey) {
    return handleInfo(sb, settingKey, "disable");
  }

  private StringBuilder handleInfo(StringBuilder sb, String settingKey, String settingValue) {
    sb.append("将 ")
        .append(settingKey)
        .append("\n设置为: ")
        .append(settingValue)
        .append("\n成功");
    return sb;
  }

  private boolean checkSettingExist(String settingKey) {
    SettingItem item = MybatisUtil.getInstance().getSingleData(SettingMapper.class, SettingItem.class, "getSetting", settingKey);
    return item == null;
  }
}
