package com.zxy.swaycamp.common.enums;

import com.zxy.swaycamp.common.constant.HttpStatus;

/**
 * 代码信息
 *
 * @author XinYuan Zhao
 * @since 2023/1/24
 */
public enum CodeMsg {

    /**
     * 成功
     */
    SUCCESS(HttpStatus.SUCCESS, "操作成功"),
    /**
     * 参数异常
     */
    PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "参数异常"),
    /**
     * 未登录/权限不足
     */
    NOT_LOGIN(HttpStatus.UNAUTHORIZED, "未授权"),
    /**
     * 登录过期
     */
    LOGIN_EXPIRED(HttpStatus.FORBIDDEN, "授权已过期，请重新登陆"),
    /**
     * 服务异常
     */
    FAIL(HttpStatus.ERROR, "服务异常");


    private final int code;
    private final String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
