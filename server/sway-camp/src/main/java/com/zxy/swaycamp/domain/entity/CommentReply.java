package com.zxy.swaycamp.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 回复表
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("comment_reply")
public class CommentReply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 回复ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 回复用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 评论ID
     */
    @TableField("comment_id")
    private Integer commentId;

    /**
     * 被回复的回复ID
     */
    @TableField("reply_id")
    private Integer replyId;

    /**
     * 被回复用户ID
     */
    @TableField("reply_user_id")
    private Integer replyUserId;

    /**
     * 回复内容
     */
    @TableField("content")
    private String content;

    /**
     * 点赞
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 回复时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 是否删除[0:未删除，1:已删除]
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
