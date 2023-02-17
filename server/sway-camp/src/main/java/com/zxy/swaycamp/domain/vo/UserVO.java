package com.zxy.swaycamp.domain.vo;

import jdk.nashorn.internal.parser.Token;
import lombok.Data;

/**
 * 返回用户信息
 *
 * @author XinYuan Zhao
 * @since 2023/1/25
 */
@Data
public class UserVO extends TokenVO {

    private Integer id;
    private String username;
    private String phoneNumber;
    private String email;
    private Integer gender;
    private String avatar;
    private String introduction;

}
