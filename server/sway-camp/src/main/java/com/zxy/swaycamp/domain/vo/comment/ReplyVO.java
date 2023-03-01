package com.zxy.swaycamp.domain.vo.comment;

import lombok.Data;

/**
 * @author XinYuan Zhao
 * @since 2023/2/28
 */
@Data
public class ReplyVO {
    private Integer id;
    private Integer commentId;
    private String content;
    private String date;
    private Integer likeCount;
    private Integer userId;
    private String username;
    private String avatar;
    private Integer replyUserId;
    private String replyUsername;
    private String replyAvatar;
}
