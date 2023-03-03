package com.zxy.swaycamp.domain.vo.article;

import lombok.Data;

/**
 * @author XinYuan Zhao
 * @since 2023/2/28
 */
@Data
public class ArticleLabelVO {

    private Integer id;
    private Integer sortId;
    private String labelName;
    private Integer articleCount;
    private String labelDescription;


}
