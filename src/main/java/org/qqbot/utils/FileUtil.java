package org.qqbot.utils;

import org.qqbot.Plugin;
import org.qqbot.mirai.MiraiMain;

import java.io.File;

public class FileUtil implements InitializeUtil {
  private static String RESOURCE_ROOT_FOLDER_PATH;
  /* linux: "/" windows: "\\" */
  private static String SYSTEM_PATH_DIV;

  private static final FileUtil INSTANCE = new FileUtil();

  public static void init() {
    File dataFolder = Plugin.INSTANCE.getDataFolder();
    RESOURCE_ROOT_FOLDER_PATH = dataFolder.toPath() + SYSTEM_PATH_DIV;
    SYSTEM_PATH_DIV = System.getProperty("os.name").startsWith("win") ? "\\" : "/";
  }

  public static FileUtil getInstance() {
    return INSTANCE;
  }

  public File getResourceFile(String fileName) {
    return getResourceFile(fileName, null);
  }

  public File getResourceFile(String fileName, String fold) {
    if (fold == null) return new File(RESOURCE_ROOT_FOLDER_PATH + fileName);
    return new File(RESOURCE_ROOT_FOLDER_PATH + fold + SYSTEM_PATH_DIV + fileName);
  }

  public File getVoiceResourceFile(String fileName, String fold) {
    if (fold == null) return new File(RESOURCE_ROOT_FOLDER_PATH + "voices"  + SYSTEM_PATH_DIV + fileName);
    return new File(RESOURCE_ROOT_FOLDER_PATH + "voices"  + SYSTEM_PATH_DIV + fold + SYSTEM_PATH_DIV + fileName);
  }
}
