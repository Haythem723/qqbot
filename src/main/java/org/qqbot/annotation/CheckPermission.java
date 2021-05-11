package org.qqbot.annotation;

import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;

import java.util.Arrays;
import java.util.Set;

public class CheckPermission {
  public static boolean checkGroupPermission(Permission permission, GroupMessageEvent event) {
    if (permission.isAt() && !event.getMessage().contains(new At(event.getBot().getId()))) return false;
    if (!checkList(permission, event)) return false;
    MemberPermission memberPermission = event.getSender().getPermission();
    if (permission.isGroupOwnerOnly()) return memberPermission == MemberPermission.OWNER;
    if (permission.isAdminOnly()) return memberPermission == MemberPermission.OWNER || memberPermission == MemberPermission.ADMINISTRATOR;
    return true;
  }

  public static boolean checkFriendPermission(Permission permission, FriendMessageEvent event) {
    return checkList(permission, event);
  }

  private static boolean checkList(Permission permission, MessageEvent event) {
    Set<String> blocks = permission.getBlocks();
    String senderId = String.valueOf(event.getSender().getId());
    if (!blocks.isEmpty() && blocks.contains(senderId)) return false;
    Set<String> allows = permission.getAllows();
    if (allows.isEmpty()) return true;
    return allows.contains(senderId);
  }

  public static Permission getGroupPermission(GroupPermission methodAnnotation, GroupPermission classAnnotation) {
    Permission permission = new Permission();
    GroupPermission check = methodAnnotation != null ? methodAnnotation : classAnnotation;
    if (check != null) {
      permission.getAllows().addAll(Arrays.asList(check.allows()));
      permission.getBlocks().addAll(Arrays.asList(check.blocks()));
      permission.setAdminOnly(check.isAdminOnly());
      permission.setGroupOwnerOnly(check.isGroupOwnerOnly());
    }
    return permission;
  }

  public static Permission getFriendPermission(FriendPermission methodAnnotation, FriendPermission classAnnotation) {
    Permission permission = new Permission();
    FriendPermission check = methodAnnotation != null ? methodAnnotation : classAnnotation;
    if (check != null) {
      permission.getAllows().addAll(Arrays.asList(check.allows()));
      permission.getBlocks().addAll(Arrays.asList(check.blocks()));
    }
    return permission;
  }
}
