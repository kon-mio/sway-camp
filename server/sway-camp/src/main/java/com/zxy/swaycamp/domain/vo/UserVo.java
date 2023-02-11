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
    private String username;
    private String phoneNumber;
    private String email;
    private Integer gender;
    private String avatar;
    private String introduction;
    private String token;

}
