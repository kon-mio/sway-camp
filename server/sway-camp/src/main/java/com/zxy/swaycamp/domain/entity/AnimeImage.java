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
 * 动漫图片表
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("anime_image")
public class AnimeImage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 动漫id
     */
    @TableField("anime_id")
    private Integer animeId;

    /**
     * 图片地址
     */
    @TableField("url")
    private String url;

    /**
     * 图片宽
     */
    @TableField("width")
    private String width;

    /**
     * 高
     */
    @TableField("height")
    private String height;

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
     * 删除标志
     */
    @TableField("is_deleted")
    private Boolean isDeleted;


}
