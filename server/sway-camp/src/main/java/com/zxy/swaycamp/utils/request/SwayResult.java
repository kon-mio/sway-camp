package com.zxy.swaycamp.utils.request;

import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.enums.CodeMsg;
import lombok.Data;

import java.io.Serializable;

/**
 * @author XinYuan Zhao
 * @date 2023/1/24
 * @apiNote
 */
@Data
public class SwayResult<T> implements Serializable {

    private static final long serialVersionUI = 1L;

    private int code;
    private String msg;
    private T data;
    private long timestamp = System.currentTimeMillis();

    /**
     * 初始化一个新创建的 SwayResult 对象，使其表示一个空消息。
     */
    public SwayResult() {
    }

    /**
     * 初始化一个新创建的 SwayResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public SwayResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public SwayResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <T> SwayResult<T> success() {
        return SwayResult.success("操作成功");
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static <T> SwayResult<T> success(String msg) {
        return SwayResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> SwayResult<T> success(T data) {
        return SwayResult.success("请求成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> SwayResult<T> success(String msg, T data) {
        return new SwayResult(HttpStatus.SUCCESS, msg, data);
    }


    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static <T> SwayResult<T> fail() {
        return SwayResult.fail();
    }

    /**
     * 返回错误消息
     *
     * @param msg 错误消息
     * @return 错误消息
     */
    public static <T> SwayResult<T> fail(String msg) {
        return new SwayResult(HttpStatus.ERROR, msg);
    }

    /**
     * 返回错误消息
     *
     * @param code 错误代码
     * @param msg  错误消息
     * @return 错误消息
     */
    public static <T> SwayResult<T> fail(Integer code, String msg) {
        return new SwayResult(code, msg);
    }

    public static <T> SwayResult<T> fail(CodeMsg codeMsg) {
        return new SwayResult(codeMsg.getCode(), codeMsg.getMsg());
    }

}
