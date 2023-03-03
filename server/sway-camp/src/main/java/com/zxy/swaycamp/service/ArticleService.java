package com.zxy.swaycamp.service;

import cn.hutool.db.Page;
import com.zxy.swaycamp.domain.dto.article.ArticleDTO;
import com.zxy.swaycamp.domain.dto.article.SearchDTO;
import com.zxy.swaycamp.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.swaycamp.domain.vo.article.ArticleVO;
import com.zxy.swaycamp.domain.vo.PageVO;

import java.util.List;

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
     * 分页查询文章
     * @param index 索引
     * @param size 大小
     * @return 文章列表
     */
    PageVO<ArticleVO> listArticle(Integer index, Integer size);

    /**
     * 分页搜索文章
     * @param searchDTO 搜索信息
     * @return 文章列表
     */
    List<ArticleVO> listSearchArticle(SearchDTO searchDTO);

    /**
     * 查询推荐文章
     * @param size 大小
     * @return 文章列表
     */
    List<ArticleVO>   listRecommend(Integer size);


    /**
     * 上传文章接口
     * @param articleDTO 文章信息
     */
    void saveArticle(ArticleDTO articleDTO);


    /**
     * 收藏文章
     * @param articleId 文章Id
     */
    void saveArticleFav(Integer articleId);

    /**
     * 取消收藏文章
     * @param articleId 文章Id
     */
    void removeArticleFav(Integer articleId);

    /**
     * 分页查询用户收藏文章
     * @param index 索引
     * @param size 大小
     * @return 文章列表
     */
    PageVO<ArticleVO> listFavArticle(Integer index, Integer size);

}
