package com.zxy.swaycamp.domain.vo.comment;

import lombok.Data;

import java.util.List;

/**
 * @author XinYuan Zhao
 * @since 2023/2/28
 */
@Data
public class CommentVO {

    private Integer id;
    private Integer userId;
    private String username;
    private String avatar;
    private String content;
    private Integer likeCount;
    private String date;
    private List<ReplyVO> replies;
    private Integer replyCount;
}
