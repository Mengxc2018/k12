package cn.k12soft.servo.security.permission;

import cn.k12soft.servo.domain.enumeration.Permission;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface PermissionRequired {

  Permission[] value();
}
