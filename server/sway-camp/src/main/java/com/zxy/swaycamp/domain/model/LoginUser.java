package com.zxy.swaycamp.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户信息类
 *
 * @author XinYuan Zhao
 * @since 2023/1/27
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户角色
     */
    private Integer userRole;


    /**
     * 登录时间
     */
    private long loginTime;

    /**
     * 过期时间
     */
    private long expireTime;

    /**
     * token
     */
    private String accessToken;


}
