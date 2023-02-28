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
 * 文章评论表
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论用户
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 文章
     */
    @TableField("article_id")
    private Integer articleId;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 点赞次数
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 回复总数
     */
    @TableField("reply_count")
    private Integer replyCount;

    /**
     * 评论时间
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
