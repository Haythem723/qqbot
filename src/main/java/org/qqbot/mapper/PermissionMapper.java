package org.qqbot.mapper;

import org.qqbot.entity.PermissionItem;
import org.qqbot.entity.SettingItem;

import java.util.List;

public interface PermissionMapper extends BaseMapper {

  PermissionItem getPermission(PermissionItem item);

  void updatePermission(PermissionItem item);

  void removePermission(PermissionItem item);
}
