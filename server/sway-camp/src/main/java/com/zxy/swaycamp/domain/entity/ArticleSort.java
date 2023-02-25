package com.zxy.swaycamp.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章类型表
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("article_sort")
public class ArticleSort implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类昵称
     */
    @TableField("sort_name")
    private String sortName;

    /**
     * 介绍
     */
    @TableField("sort_description")
    private String sortDescription;

    /**
     * 是否启用[0:未删除，1:已删除]
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean isDeleted;

    @TableField(exist = false)
    private Integer countOfSort;

    @TableField(exist = false)
    private List<ArticleLabel> labels;


}
