package com.zxy.swaycamp.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 返回用户信息
 *
 * @author XinYuan Zhao
 * @since 2023/1/25
 */
@Data
public class UserVo {

    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String phoneNumber;

    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    private Integer gender;

    private String avatar;

    private String introduction;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String updateBy;

    private Boolean isBoss = false;

    private String accessToken;

    private String code;
}
