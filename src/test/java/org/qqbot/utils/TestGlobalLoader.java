package org.qqbot.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TestGlobalLoader {
  @Test
  public void testGetClassName() {
    System.out.println(File.separatorChar);
    GlobalLoader.init();
  }
}
