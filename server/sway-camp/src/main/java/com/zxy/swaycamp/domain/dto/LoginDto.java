package com.zxy.swaycamp.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 登录参数
 *
 * @author XinYuan Zhao
 * @since 2023/1/24
 */
@Data
public class LoginDto {
    @NotEmpty
    private String account;

    @NotEmpty
    private String password;
}
