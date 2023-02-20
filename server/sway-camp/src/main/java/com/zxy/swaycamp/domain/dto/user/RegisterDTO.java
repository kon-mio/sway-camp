package com.zxy.swaycamp.domain.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 验证码登录/注册参数
 *
 * @author XinYuan Zhao
 * @since 2023/2/10
 */
@Data
public class RegisterDTO {
    @NotEmpty
    private String account;

    @NotNull
    private Integer code;
}
