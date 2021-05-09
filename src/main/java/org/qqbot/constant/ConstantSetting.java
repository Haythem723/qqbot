package org.qqbot.constant;

import java.util.List;
import java.util.Arrays;

public class ConstantSetting {
  public static final List<Long> adminList = Arrays.asList(
      1355247243L,
      1328343252L
  );

  public enum SettingOperates {
    OPERATE_NULL("null"),
    OPERATE_SET("set"),
    OPERATE_DISABLE("disable"),
    OPERATE_ENABLE("enable")
    ;
    private final String operate;

    private SettingOperates(String operate) {
      this.operate = operate;
    }

    public static SettingOperates getOperateByString(String s) {
      for (SettingOperates operate : SettingOperates.values()) {
        if (operate.operate.equals(s)) return operate;
      }
      return OPERATE_NULL;
    }
  }

  public static final String SETTING_ALLOW_R18 = "allow_r18";
}
