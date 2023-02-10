package com.zxy.swaycamp.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 邮箱登录/注册参数
 *
 * @author XinYuan Zhao
 * @since 2023/2/10
 */
@Data
public class RegisterDto {
    @NotEmpty
    private String account;

    @NotEmpty
    private String password;
}
