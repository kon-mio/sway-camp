package com.zxy.swaycamp.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章标签表
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("article_label")
public class ArticleLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类ID
     */
    @TableField("sort_id")
    private Integer sortId;

    /**
     * 标签名称
     */
    @TableField("label_name")
    private String labelName;

    /**
     * 标签描述
     */
    @TableField("label_description")
    private String labelDescription;

    /**
     * 是否启用[0:未删除，1:已删除]
     */
    @TableField("is_delete")
    private Boolean isDelete;

    @TableField(exist = false)
    private Integer countOfLabel;

}
