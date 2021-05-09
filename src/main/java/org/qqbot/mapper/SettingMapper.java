package org.qqbot.mapper;

import org.qqbot.entity.SettingItem;

import java.util.List;

public interface SettingMapper extends BaseMapper {

  List<SettingItem> loadSettings();

  SettingItem getSetting(String settingKey);

  void setSetting(String settingKey, String settingValue);

  void enableSetting(String settingKey);

  void disableSetting(String settingKey);

}
