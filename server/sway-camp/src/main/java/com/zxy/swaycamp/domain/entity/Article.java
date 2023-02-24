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
 * 
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 类型
     */
    @TableField("sort_id")
    private Integer sortId;

    /**
     * 标签
     */
    @TableField("label_id")
    private Integer labelId;

    /**
     * 封面
     */
    @TableField("cover")
    private String cover;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 介绍
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 转载链接
     */
    @TableField("reprinted")
    private String reprinted;

    /**
     * 是否转载[0:否，1:是]
     */
    @TableField("reprinted_status")
    private Boolean reprintedStatus;

    /**
     * 阅读量
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 收藏量
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 是否可见[0:否，1:是]
     */
    @TableField("view_status")
    private Boolean viewStatus;

    /**
     * 是否启用评论[0:否，1:是]
     */
    @TableField("comment_status")
    private Boolean commentStatus;

    /**
     * 是否推荐文章[0:否，1:是]
     */
    @TableField("recommend_status")
    private Boolean recommendStatus;

    /**
     * 是否已经审核[0:否，1:是]
     */
    @TableField("review")
    private Boolean review;

    /**
     * 是否审核通过[0:否，1:是]
     */
    @TableField("review_status")
    private Boolean reviewStatus;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 更新用户
     */
    @TableField("update_by")
    private Integer updateBy;

    /**
     * 是否删除[0:未删除，1:已删除]
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
