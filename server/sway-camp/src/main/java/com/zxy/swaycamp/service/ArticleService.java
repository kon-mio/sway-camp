package com.zxy.swaycamp.service;

import com.zxy.swaycamp.domain.dto.article.ArticleDTO;
import com.zxy.swaycamp.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.swaycamp.domain.vo.ArticleVO;

/**
 * <p>
 * 文章 服务类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-24
 */
public interface ArticleService extends IService<Article> {

    /**
     * 根据Id查询文章
     * @param articleId 文章ID
     * @return 文章详细信息
     */
    ArticleVO getArticle(Integer articleId);

    /**
     * 上传文章接口
     * @param articleDTO 文章信息
     */
    void uploadArticle(ArticleDTO articleDTO);

}
