package com.zxy.swaycamp.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 更新用户信息
 *
 * @author XinYuan Zhao
 * @since 2023/2/20
 */
@Data
public class UpdateUserInfoDTO {
    @NotEmpty
    private String username;
    @NotNull
    private Integer gender;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;
}
