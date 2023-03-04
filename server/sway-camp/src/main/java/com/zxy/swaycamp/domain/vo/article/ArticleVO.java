package com.zxy.swaycamp.domain.vo.article;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 返回文章信息
 *
 * @author XinYuan Zhao
 * @since 2023/2/24
 */
@Data
public class ArticleVO {

    private Integer id;

    private Integer userId;

    private String username;
    private String sort;

    private String label;

    private String cover;

    private String title;

    private String content;

    private String introduction;

    private String reprinted;

    private Integer viewCount;

    private Integer likeCount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isFav;

}
