package org.qqbot.annotation;

import java.lang.annotation.*;

/**
 * @author diyigemt
 * 好友消息权限规定
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FriendPermission {

  String[] allows() default {};

  String[] blocks() default {};
}
