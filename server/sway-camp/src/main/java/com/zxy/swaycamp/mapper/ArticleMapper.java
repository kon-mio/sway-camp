package com.zxy.swaycamp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zxy.swaycamp.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-24
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 分页查询文章
     * @param page 分页信息
     * @return 文章列表
     */
    List<Article> listArticlePage(IPage<Article> page);
}
