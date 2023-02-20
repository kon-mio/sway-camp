package com.zxy.swaycamp.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *  用户信息表
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-01-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户SID
     */
    @TableField("sway_id")
    private String swayId;


    /**
     * 用户昵称
     */
    @TableField("username")
    private String username;

    /**
     * 手机号
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别[0:：保密，1：男，2：女]
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 简介
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 生日
     */
    @TableField("birthday")
    private LocalDateTime birthday;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最终修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 最终修改人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * openId
     */
    @TableField("open_id")
    private String openId;

    /**
     * 用户角色
     */
    @TableField("user_role")
    private Integer userRole;

    /**
     * 状态[1：正常，0：未启用]
     */
    @TableField("user_status")
    private Boolean userStatus;

    /**
     * 是否删除[1：已删除， 0：未删除]
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
