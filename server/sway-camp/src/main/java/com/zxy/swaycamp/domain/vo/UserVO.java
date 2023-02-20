package com.zxy.swaycamp.domain.vo;

import jdk.nashorn.internal.parser.Token;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 返回用户信息
 *
 * @author XinYuan Zhao
 * @since 2023/1/25
 */
@Data
public class UserVO extends TokenVO {

    private Integer id;
    private String swayId;
    private String role;
    private String username;
    private String email;
    private String phoneNumber;
    private Integer gender;
    private String avatar;
    private String introduction;
    private LocalDateTime birthday;
    private LocalDateTime createTime;



}
