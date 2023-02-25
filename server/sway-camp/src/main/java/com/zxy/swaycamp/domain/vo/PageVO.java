package com.zxy.swaycamp.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页数据
 *
 * @author XinYuan Zhao
 * @since 2023/2/25
 */
@Data
public class PageVO<T>{
    private List<T> list;

    private Integer total;
}
