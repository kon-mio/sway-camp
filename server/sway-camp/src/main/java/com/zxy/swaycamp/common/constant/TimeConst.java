package com.zxy.swaycamp.common.constant;

/**
 * 时间常量
 *
 * @author XinYuan Zhao
 * @since 2023/1/27
 */
public class TimeConst {

    private TimeConst(){}

    /**
     * Token过期时间：7天
     */
    public static final Integer TOKEN_EXPIRE_REFRESH = 30;
    public static final Integer TOKEN_EXPIRE_ACCESS = 7;

    /**
     * 验证码过期时间
     */
    public static final Integer TOKEN_CODE = 5;



    /**
     * 时间戳 1s
     */
    public static final long MILLIS_SECOND = 1000;
    /**
     * 时间戳 1m
     */
    public static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    /**
     * 时间戳 1h
     */
    public static final long MILLIS_HOUR = 60 * MILLIS_MINUTE;
    /**
     * 时间戳 1d
     */
    public static final long MILLIS_DAY = 24 * MILLIS_HOUR;
    /**
     * 时间戳 7d
     */
    public static final long MILLIS_DAY_SEVEN = 7 * 24 * MILLIS_HOUR;

}
