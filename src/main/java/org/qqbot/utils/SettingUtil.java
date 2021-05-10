package org.qqbot.utils;

import org.qqbot.entity.SettingItem;
import org.qqbot.mapper.SettingMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingUtil implements InitializeUtil {
  private static final SettingUtil INSTANCE = new SettingUtil();
  private static final Map<String, String> STORE = new HashMap<String, String>();

  public static void init() {
    List<SettingItem> settings = MybatisUtil.getInstance().getListData(SettingMapper.class, SettingItem.class, "loadSettings");
    for (SettingItem item : settings) {
      STORE.put(item.getKey(), item.getValue());
    }
  }

  public static SettingUtil getInstance() { return INSTANCE; }

  public String get(String key) {
    return STORE.get(key);
  }

  public String get(String key, String defaultValue) {
    String s = STORE.get(key);
    return s == null ? defaultValue : s;
  }

  public boolean set(String key, String value) {
    int res = MybatisUtil.getInstance().updateData(SettingMapper.class, Integer.class, "setSetting", key, value);
    if (res != 0) return false;
    STORE.put(key, value);
    return true;
  }

  public boolean set(SettingItem item) {
    int res = MybatisUtil.getInstance().updateData(SettingMapper.class, Integer.class, "setSetting", item.getKey(), item.getValue());
    if (res != 0) return false;
    STORE.put(item.getKey(), item.getValue());
    return true;
  }

  public boolean setBooleanValue(String key, boolean value) {
    int res = -1;
    if (value) {
      res = MybatisUtil.getInstance().updateData(SettingMapper.class, Integer.class, "enableSetting", key);
    } else {
      res = MybatisUtil.getInstance().updateData(SettingMapper.class, Integer.class, "disableSetting", key);
    }
    if (res != 0) return false;
    STORE.put(key, String.valueOf(value));
    return true;
  }

  public boolean getBooleanValue(String key) {
    String s = get(key);
    return Boolean.parseBoolean(s);
  }

  public boolean getBooleanValue(String key, boolean defaultValue) {
    String s = get(key);
    if (s == null) return defaultValue;
    return Boolean.parseBoolean(s);
  }
}
