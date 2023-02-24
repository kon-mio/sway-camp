package com.zxy.swaycamp.domain.dto.article;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文章所需参数
 *
 * @author XinYuan Zhao
 * @since 2023/2/24
 */
@Data
public class ArticleDTO {

    @NotNull(message = "文章分类不能为空")
    private Integer sortId;

    @NotNull(message = "文章标签不能为空")
    private Integer labelId;

    @NotBlank(message = "文章标题不能为空")
    private String title;

    @NotBlank(message = "文章内容不能为空")
    private String content;

    @NotBlank(message = "文章简介不能为空")
    private String introduction;

    private String reprinted;

    private Boolean commentStatus;

    private Boolean viewStatus;

    private MultipartFile cover;


}
