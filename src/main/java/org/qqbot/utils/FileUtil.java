package org.qqbot.utils;

import org.qqbot.Plugin;
import org.qqbot.mirai.MiraiMain;

import java.io.File;

public class FileUtil implements InitializeUtil {
  private static String RESOURCE_ROOT_FOLDER_PATH;

  public static void init() {
    File dataFolder = Plugin.INSTANCE.getDataFolder();
    RESOURCE_ROOT_FOLDER_PATH = dataFolder.toPath().toString() + osPath();
  }

  public static File getResourceFile(String fileName) {
    return getResourceFile(fileName, null);
  }

  public static File getResourceFile(String fileName, String fold) {
    if (fold == null) return new File(RESOURCE_ROOT_FOLDER_PATH + fileName);
    return new File(RESOURCE_ROOT_FOLDER_PATH + fold + osPath() + fileName);
  }

  public static File getVoiceResourceFile(String fileName, String fold) {
    if (fold == null) return new File(RESOURCE_ROOT_FOLDER_PATH + "voices"  + osPath() + fileName);
    return new File(RESOURCE_ROOT_FOLDER_PATH + "voices"  + osPath() + fold + osPath() + fileName);
  }

  private static String osPath() {
    return MiraiMain.DEVELOPMENT ? "\\" : "/";
  }
}
