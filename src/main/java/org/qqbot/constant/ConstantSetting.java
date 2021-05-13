package org.qqbot.constant;

public class ConstantSetting {

  public enum SettingOperate {
    OPERATE_NULL("null"),
    OPERATE_SET("set"),
    OPERATE_DISABLE("disable"),
    OPERATE_ENABLE("enable"),
    OPERATE_PERMIT("permit")
    ;
    private final String operate;

    SettingOperate(String operate) {
      this.operate = operate;
    }

    public static SettingOperate getOperateByString(String s) {
      for (SettingOperate operate : SettingOperate.values()) {
        if (operate.operate.equals(s)) return operate;
      }
      return OPERATE_NULL;
    }
  }

  public enum PermitOperate {
    OPERATE_NULL("null"),
    OPERATE_ADD("add"),
    OPERATE_REMOVE("remove"),
    OPERATE_DISABLE("disable"),
    OPERATE_ENABLE("enable")
    ;

    private final String operate;

    PermitOperate(String operate) { this.operate = operate; }

    public static PermitOperate getPermitOperateByString(String s) {
      for (PermitOperate operate : PermitOperate.values()) {
        if (operate.operate.equals(s)) return operate;
      }
      return OPERATE_NULL;
    }
  }

  // 是否允许搜索r18图片
  public static final String SETTING_ALLOW_R18 = "allow_r18";
}
