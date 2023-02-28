package com.zxy.swaycamp.domain.dto.article;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author XinYuan Zhao
 * @since 2023/2/28
 */
@Data
public class SearchDTO {
    @NotNull
    private Integer index;
    @NotNull
    private Integer size;
    private Integer sort;
    private String keyword;
}
