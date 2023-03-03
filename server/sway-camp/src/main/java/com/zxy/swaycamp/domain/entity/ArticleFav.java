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
 * @since 2023-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("article_fav")
public class ArticleFav implements Serializable {

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
     * 文章
     */
    @TableField("article_id")
    private Integer articleId;

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
     * 状态
     */
    @TableField("fav_status")
    private Boolean favStatus;

    /**
     * 删除标志
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean isDeleted;


}
