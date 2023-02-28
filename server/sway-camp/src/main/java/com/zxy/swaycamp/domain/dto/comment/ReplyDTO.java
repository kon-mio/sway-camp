package com.zxy.swaycamp.domain.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author XinYuan Zhao
 * @since 2023/2/28
 */
@Data
public class ReplyDTO {

    @NotNull
    private Integer commentId;
    @NotEmpty
    private String content;

    private Integer replyId;
    private Integer replyUserId;

}
