package org.qqbot;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;

public class TestSimple {

  @Test
  public void testSet() {
    Set<Object> objects = new HashSet<Object>();
    objects.addAll(Arrays.asList("123123", "12312312"));
    System.out.println(objects.toString());
  }

  @Test
  public void testImp() {
    A a = new AA();
    A b = new BB();
    System.out.println(a.getClass().toString());
    System.out.println(b.getClass().toString());
  }

  interface A {
    void say();
  }

  @Target({ElementType.METHOD, ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @interface B {

  }

  class AA implements A {

    @Override
    @B
    public void say() {
      System.out.println("AA");
    }
  }

  class BB implements A {

    @Override
    @B
    public void say() {
      System.out.println("BB");
    }
  }
}
