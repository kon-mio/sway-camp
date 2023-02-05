package com.zxy.swaycamp.annotation;

import com.zxy.swaycamp.common.enums.Action;
import java.lang.annotation.*;

/**
 * 请求日志注解
 *
 * @author XinYuan Zhao
 * @since 2023/1/25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Log {

    /**
     * 模块
     */
    String title() default "";

    /**
     * Action
     */
    Action action() default Action.OTHER;
}
