package com.zxy.swaycamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.article.ArticleDTO;
import com.zxy.swaycamp.domain.dto.article.SearchDTO;
import com.zxy.swaycamp.domain.entity.Article;
import com.zxy.swaycamp.domain.entity.ArticleLabel;
import com.zxy.swaycamp.domain.entity.ArticleSort;
import com.zxy.swaycamp.domain.entity.User;
import com.zxy.swaycamp.domain.vo.ArticleVO;
import com.zxy.swaycamp.domain.vo.FileVO;
import com.zxy.swaycamp.domain.vo.PageVO;
import com.zxy.swaycamp.mapper.ArticleMapper;
import com.zxy.swaycamp.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.service.OssService;
import com.zxy.swaycamp.utils.SwayUtil;
import com.zxy.swaycamp.utils.query.CommonQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-24
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Resource
    private OssService ossService;

    @Resource
    private CommonQuery commonQuery;

    @Resource
    private ArticleMapper articleMapper;


    /**
     * 根据Id查询文章
     * @param articleId 文章ID
     * @return 文章详细信息
     */
    @Override
    public ArticleVO getArticle(Integer articleId){
        Article article = lambdaQuery().eq(Article::getId, articleId)
                .eq(Article::getDeleted, false)
                .eq(Article::getReview, true)
                .one();
        if(article == null){
            throw new ServiceException(HttpStatus.NOT_FOUND, "文章不存在");
        }
        Integer userId = SwayUtil.getCurrentUserId();
        if(!article.getViewStatus()){
            if(userId == null || !userId.equals(article.getUserId())){
                throw new ServiceException(HttpStatus.NOT_FOUND, "权限不足");
            }
        }
        return buildArticleVO(article);
    }

    /**
     * 分页查询文章
     * @param index 索引
     * @param size 大小
     * @return 文章列表
     */
    @Override
    public PageVO<ArticleVO> listArticle(Integer index, Integer size){
        Page<Article> articles = lambdaQuery().page(new Page<>(index, size));
        if(articles == null || CollectionUtils.isEmpty(articles.getRecords())){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "查询页数超出总页数");
        }
        List<ArticleVO> articleVos =  articles.getRecords().stream().map(item -> buildArticleVO(item)).collect(Collectors.toList());
        PageVO<ArticleVO> pageVO = new PageVO<>();
        pageVO.setList(articleVos);
        pageVO.setTotal((int) articles.getTotal());
        return pageVO;
    }

    /**
     * 分页搜索文章
     * @return 文章列表
     */
    @Override
    public List<ArticleVO>  listSearchArticle(SearchDTO searchDTO){
        Page<Article> articles = new Page<>();
        if(searchDTO.getSort() == null && searchDTO.getKeyword() == null){
            articles = lambdaQuery().page(new Page<>(searchDTO.getIndex(),  searchDTO.getSize()));
        }
        if(searchDTO.getSort() != null && searchDTO.getKeyword() == null){
            articles = lambdaQuery().eq(Article::getSortId, searchDTO.getSort())
                    .page(new Page<>(searchDTO.getIndex(),  searchDTO.getSize()));
        }
        if(searchDTO.getSort() == null && searchDTO.getKeyword() != null){
            articles = lambdaQuery().like(Article::getTitle, searchDTO.getKeyword())
                    .page(new Page<>(searchDTO.getIndex(),  searchDTO.getSize()));
        }
        if(searchDTO.getSort() != null && searchDTO.getKeyword() != null){
            articles = lambdaQuery().eq(Article::getSortId, searchDTO.getSort())
                    .like(Article::getTitle, searchDTO.getKeyword())
                    .page(new Page<>(searchDTO.getIndex(),  searchDTO.getSize()));
        }
        return articles.getRecords().stream().map(item ->{
            ArticleVO articleVO = buildArticleVO(item);
            articleVO.setContent(null);
            return articleVO;
        }).collect(Collectors.toList());

    }

    /**
     * 查询推荐文章
     * @param size 大小
     * @return 文章列表
     */
    @Override
    public  List<ArticleVO>  listRecommend(Integer size){
        List<Article> articles = lambdaQuery().eq(Article::getDeleted, false)
                .eq(Article::getReview, true)
                .orderByDesc(Article::getViewCount)
                .last("limit 0, " + size )
                .list();
        List<ArticleVO> articleVos =  articles.stream().map(item -> {
                    item.setContent(null);
                    return buildArticleVO(item);
                }).collect(Collectors.toList());
        return articleVos;
    }


    /**
     * 上传文章接口
     * @param articleDTO 文章信息
     */

    @Override
    public void uploadArticle(ArticleDTO articleDTO){
        Integer userId = SwayUtil.getCurrentUserId();
        FileVO fileVo = ossService.uploadImage(articleDTO.getCover(), userId, "image/cover/");
        if(fileVo == null){
            throw new ServiceException(CodeMsg.PARAMETER_ERROR);
        }
        try{
            Article article = new Article();
            BeanUtils.copyProperties(articleDTO, article);
            if(StringUtils.hasText(articleDTO.getReprinted())){
                article.setReprintedStatus(true);
            }else{
                article.setReprinted(null);
                article.setReprintedStatus(false);
            }
            article.setUserId(userId);
            article.setCover(fileVo.getUrl());
            article.setLikeCount(0);
            article.setViewCount(0);
            article.setCreateTime(LocalDateTime.now());
            article.setUpdateTime(LocalDateTime.now());
            article.setUpdateBy(userId);
            article.setReview(false);
            article.setReviewStatus(false);
            article.setDeleted(false);
            article.setRecommendStatus(false);
            save(article);

        }catch (Exception e){
            logger.error("上传文章报错：{}", e.getMessage());
            throw new ServiceException();
        }
    }



    private ArticleVO buildArticleVO(Article article){
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        User user = commonQuery.getUser(article.getUserId());
        articleVO.setUsername(user.getUsername());
        List<ArticleSort> sortInfo = commonQuery.getSortInfo();
        if (sortInfo != null) {
            for (ArticleSort s : sortInfo) {
                if (s.getId().equals(article.getSortId())) {
                    articleVO.setSort(s.getSortName());
                    if (!CollectionUtils.isEmpty(s.getLabels())) {
                        for (int j = 0; j < s.getLabels().size(); j++) {
                            ArticleLabel l = s.getLabels().get(j);
                            if (l.getId().equals(article.getSortId())) {
                                articleVO.setLabel(l.getLabelName());
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        return articleVO;
    }
}
