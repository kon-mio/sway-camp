package com.zxy.swaycamp.domain.vo;

import lombok.Data;

/**
 * token
 *
 * @author XinYuan Zhao
 * @since 2023/2/17
 */
@Data
public class TokenVO {
    private String accessToken;

    private String refreshToken;
}
