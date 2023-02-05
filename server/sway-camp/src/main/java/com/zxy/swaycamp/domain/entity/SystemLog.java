package com.zxy.swaycamp.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *  系统日志表
 * </p>
 *
 * @author mio
 * @since 2023-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("system_log")
public class SystemLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("request_user")
    private Integer requestUser;

    /**
     * 操作模块
     */
    @TableField("title")
    private String title;

    /**
     * 操作方法
     */
    @TableField("method")
    private String method;

    /**
     * 操作类型[POST/GET]
     */
    @TableField("type")
    private String type;

    /**
     * 操作行为[增删改查]
     */
    @TableField("action")
    private String action;

    /**
     * 请求参数
     */
    @TableField("param")
    private String param;

    /**
     * 返回参数
     */
    @TableField("result")
    private String result;

    /**
     * 请求IP
     */
    @TableField("request_ip")
    private String requestIp;

    /**
     * 请求IP地址
     */
    @TableField("address")
    private String address;

    /**
     * 浏览器
     */
    @TableField("browser")
    private String browser;

    /**
     * 操作系统
     */
    @TableField("request_system")
    private String requestSystem;

    /**
     * 请求时间
     */
    @TableField("request_time")
    private LocalDateTime requestTime;

    /**
     * 错误消息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 请求状态
     */
    @TableField("status")
    private Boolean status;


}
