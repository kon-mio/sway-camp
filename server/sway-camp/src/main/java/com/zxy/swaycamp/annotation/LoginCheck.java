package com.zxy.swaycamp.annotation;

import com.zxy.swaycamp.common.constant.RoleConst;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录权限校验注解
 *
 * @author XinYuan Zhao
 * @since 2023/1/25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginCheck {

    /**
     * 权限
     */
    int value() default RoleConst.ROLE_USER;
}
