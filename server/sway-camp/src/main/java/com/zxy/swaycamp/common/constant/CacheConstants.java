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
    public static final String LOGIN_TOKEN_ACCESS_KEY = "sway_token_access:";
    public static final String LOGIN_TOKEN_REFRESH_KEY = "sway_token_refresh:";

    /**
     * 登录用户token redis key
     */
    public static final String LOGIN_USER_KEY = "sway_login_user:";

    /**
     * 用户信息
     */
    public static final String USER_KEY = "sway_user:";

    /**
     * 邮箱注册/登录验证码
     */
    public static final String EMAIL_CODE = "sway_email_code:";
    public static final String PHONE_CODE = "sway_phone_code:";

}
