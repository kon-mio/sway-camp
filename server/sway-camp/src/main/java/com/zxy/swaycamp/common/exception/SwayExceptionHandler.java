package com.zxy.swaycamp.common.exception;

import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.utils.request.SwayResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.validation.ConstraintViolationException;

/**
 * 自定义全局异常处理器
 *
 * @author XinYuan Zhao
 * @since 2023/1/24
 */
@Slf4j
@RestControllerAdvice
public class SwayExceptionHandler {


    /**
     * 处理非空校验错误
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public SwayResult<String> constraintExceptionHandler(Exception ex) {
        log.error("非空校验出错-----------------" + ex.getMessage());
        ConstraintViolationException e = (ConstraintViolationException) ex;
        // 错误信息提取
        String paramError = e.getMessage().split(",")[0];
        String param = paramError.split("\\.")[1].split(": ")[0];
        String msg = paramError.split(": ")[1];
        String errorMsg = "参数" + param + msg;
        return SwayResult.fail(HttpStatus.BAD_REQUEST, errorMsg);
    }

    @ExceptionHandler(ServiceException.class)
    public SwayResult<String> serviceExceptionHandler(ServiceException ex) {
        log.warn("业务出错-----------------" + ex.getMessage());
        if (ex.getCode() != null) {
            return SwayResult.fail(ex.getCode(), ex.getMsg());
        } else {
            return SwayResult.fail(ex.getMessage());
        }
    }

    /**
     * 参数校验  @requestBody @Validated @Valid 前端提交的方式为json格式有效，出现异常时会被该异常类处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SwayResult<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("JSON参数错误-----------------" + ex.getMessage());
        return SwayResult.fail(HttpStatus.BAD_REQUEST, "参数错误");
    }
}