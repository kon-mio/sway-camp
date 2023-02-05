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
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 登录用户token redis key
     */
    public static final String LOGIN_USER_KEY = "login_users:";


    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";


    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

}
