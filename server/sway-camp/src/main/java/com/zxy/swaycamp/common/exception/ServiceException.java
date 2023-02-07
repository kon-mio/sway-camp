package com.zxy.swaycamp.common.exception;

import com.zxy.swaycamp.common.enums.CodeMsg;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author Xinyuan Zhao
 */
@Getter
public class ServiceException extends RuntimeException {
    private Integer code;
    private String msg;

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
    }


    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public ServiceException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public ServiceException(Integer code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}