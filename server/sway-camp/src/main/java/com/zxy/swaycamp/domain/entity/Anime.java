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
 * 动漫信息表
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("anime")
public class Anime implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 中文昵称
     */
    @TableField("name")
    private String name;

    /**
     * 原版昵称
     */
    @TableField("original_name")
    private String originalName;

    /**
     * 地区
     */
    @TableField("region")
    private String region;

    /**
     * 封面
     */
    @TableField("cover")
    private String cover;

    /**
     * 简介
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 官网
     */
    @TableField("official_website")
    private String officialWebsite;

    /**
     * 放送时间
     */
    @TableField("broadcast_time")
    private LocalDateTime broadcastTime;

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
    @TableLogic
    private Boolean isDeleted;


}
