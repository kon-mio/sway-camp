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
 * 文件信息表
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sway_file")
public class SwayFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件类型
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件昵称
     */
    @TableField("name")
    private String name;

    /**
     * 唯一标识
     */
    @TableField("md5")
    private String md5;

    /**
     * 相对地址
     */
    @TableField("relative_path")
    private String relativePath;

    /**
     * 文件地址
     */
    @TableField("url")
    private String url;

    /**
     * 提交昵称
     */
    @TableField("submit_name")
    private String submitName;

    /**
     * 扩展名
     */
    @TableField("extension")
    private String extension;

    /**
     * 文件大小
     */
    @TableField("size")
    private String size;

    /**
     * 文件描述
     */
    @TableField("description")
    private String description;

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
     * 文件状态
     */
    @TableField("status")
    private Boolean status;

    /**
     * 删除标志
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean isDeleted;


}
