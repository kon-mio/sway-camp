package com.zxy.swaycamp.domain.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author XinYuan Zhao
 * @since 2023/2/28
 */
@Data
public class CommentDTO {
    @NotNull
    private Integer articleId;

    @NotEmpty
    private String content;
}
