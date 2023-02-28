package com.zxy.swaycamp.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author XinYuan Zhao
 * @since 2023/2/28
 */
@Data
public class ArticleSortVO {
    private Integer id;
    private String sortName;
    private String sortDescription;
    private Integer articleCount;
    private List<ArticleLabelVO> labels;

}
