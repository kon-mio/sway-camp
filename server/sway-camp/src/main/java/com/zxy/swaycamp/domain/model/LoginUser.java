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
    private String username;

    /**
     * 用户角色
     */
    private Integer userRole;

}
