package org.qqbot.function;

import org.junit.jupiter.api.Test;

public class TestSaucenao {
  @Test
  public void test() {
    String img = Saucenao.getImg("https://lychee.diyigemt.net/uploads/small/c67a56f170c3c830b830fb1b33004d74.jpg");
    System.out.println(img);
  }
}
