package com.zxy.swaycamp.common.constant;

/**
 * 缓存的key 常量
 *
 * @author XinYuan Zhao
 * @since 2023/1/25
 */
public class CacheConstants {

    private CacheConstants() {}

    /**
     * 登录用户token redis key
     */
    public static final String LOGIN_TOKEN_KEY = "sway_token:";

    /**
     * 登录用户token redis key
     */
    public static final String LOGIN_USER_KEY = "sway_user:";

    /**
     * 邮箱注册/登录验证码
     */
    public static final String REGISTER_EMAIL_CODE = "sway_register_email_code:";

}
