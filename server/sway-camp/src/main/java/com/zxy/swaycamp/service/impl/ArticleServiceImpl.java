package com.zxy.swaycamp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.article.ArticleDTO;
import com.zxy.swaycamp.domain.dto.article.SearchDTO;
import com.zxy.swaycamp.domain.entity.*;
import com.zxy.swaycamp.domain.vo.article.ArticleVO;
import com.zxy.swaycamp.domain.vo.FileVO;
import com.zxy.swaycamp.domain.vo.PageVO;
import com.zxy.swaycamp.mapper.ArticleFavMapper;
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
import java.util.List;
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

    @Resource
    private ArticleFavMapper articleFavMapper;


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
        Page<Article> articles = lambdaQuery()
                .select(Article.class, info -> !info.getColumn().equals("content"))
                .page(new Page<>(index, size));
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
     * 分页查询用户收藏文章
     * @param index 索引
     * @param size 大小
     * @return 文章列表
     */
    @Override
    public PageVO<ArticleVO> listFavArticle(Integer index, Integer size){
        Integer userId = SwayUtil.getCurrentUserId();
        // 用户收藏文章列表
        Page<ArticleFav> articleFavPage = new LambdaQueryChainWrapper<>(articleFavMapper)
                .eq(ArticleFav::getUserId, userId)
                .eq(ArticleFav::getFavStatus, true)
                .page(new Page<>(index, size));
        if(articleFavPage == null || CollectionUtils.isEmpty(articleFavPage.getRecords())){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "查询页数超出总页数");
        }
        List<ArticleVO> articleVos = articleFavPage.getRecords().stream().filter(item -> hasArticle(item.getArticleId())).map(item -> {
            Article article = lambdaQuery().select(Article.class, info -> !info.getColumn().equals("content"))
                    .eq(Article::getId, item.getArticleId())
                    .eq(Article::getDeleted, false)
                    .one();
            return buildArticleVO(article);
        }).collect(Collectors.toList());
        PageVO<ArticleVO> pageVO = new PageVO<>();
        pageVO.setList(articleVos);
        pageVO.setTotal((int) articleFavPage.getTotal());
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
            articles = lambdaQuery()
                    .select(Article.class, info -> !info.getColumn().equals("content"))
                    .page(new Page<>(searchDTO.getIndex(),  searchDTO.getSize()));
        }
        if(searchDTO.getSort() != null && searchDTO.getKeyword() == null){
            articles = lambdaQuery()
                    .select(Article.class, info -> !info.getColumn().equals("content"))
                    .eq(Article::getSortId, searchDTO.getSort())
                    .page(new Page<>(searchDTO.getIndex(),  searchDTO.getSize()));
        }
        if(searchDTO.getSort() == null && searchDTO.getKeyword() != null){
            articles = lambdaQuery()
                    .select(Article.class, info -> !info.getColumn().equals("content"))
                    .like(Article::getTitle, searchDTO.getKeyword())
                    .page(new Page<>(searchDTO.getIndex(),  searchDTO.getSize()));
        }
        if(searchDTO.getSort() != null && searchDTO.getKeyword() != null){
            articles = lambdaQuery()
                    .select(Article.class, info -> !info.getColumn().equals("content"))
                    .eq(Article::getSortId, searchDTO.getSort())
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
    public void saveArticle(ArticleDTO articleDTO){
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

    /**
     * 收藏文章
     * @param articleId 文章Id
     */
    @Override
    public void saveArticleFav(Integer articleId){
        Integer userId = SwayUtil.getCurrentUserId();
        Article article = lambdaQuery().select(Article.class, info -> !info.getColumn().equals("content"))
                .eq(Article::getId, articleId)
                .eq(Article::getDeleted, false)
                .one();
        if(article == null){
            throw new ServiceException("收藏文章不存在");
        }
        try{
            ArticleFav articleFav = new LambdaQueryChainWrapper<>(articleFavMapper)
                    .eq(ArticleFav::getArticleId, articleId)
                    .eq(ArticleFav::getUserId, userId)
                    .eq(ArticleFav::getIsDeleted, false)
                    .one();
            if(articleFav == null){
                ArticleFav saveFav = new ArticleFav();
                saveFav.setArticleId(articleId);
                saveFav.setUserId(userId);
                saveFav.setCreateTime(LocalDateTime.now());
                saveFav.setUpdateTime(LocalDateTime.now());
                saveFav.setIsDeleted(false);
                saveFav.setFavStatus(true);
                articleFavMapper.insert(saveFav);
            }
            else{
                if(articleFav.getFavStatus()){
                    throw new ServiceException("请勿重复收藏文章");
                }
                new LambdaUpdateChainWrapper<>(articleFavMapper)
                        .eq(ArticleFav::getArticleId, articleId)
                        .eq(ArticleFav::getUserId, userId)
                        .eq(ArticleFav::getIsDeleted, false)
                        .set(ArticleFav::getUpdateTime, LocalDateTime.now())
                        .set(ArticleFav::getFavStatus, true)
                        .update();
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new ServiceException("收藏失败");
        }
    }

    /**
     * 取消收藏文章
     * @param articleId 文章Id
     */
    @Override
    public void removeArticleFav(Integer articleId){
        Integer userId = SwayUtil.getCurrentUserId();
        try{
            Article article = lambdaQuery().select(Article.class, info -> !info.getColumn().equals("content"))
                    .eq(Article::getId, articleId)
                    .eq(Article::getDeleted, false)
                    .one();
            if(article == null){
                throw new ServiceException("文章不存在");
            }
            ArticleFav articleFav = new LambdaQueryChainWrapper<>(articleFavMapper)
                    .eq(ArticleFav::getArticleId, articleId)
                    .eq(ArticleFav::getUserId, userId)
                    .eq(ArticleFav::getFavStatus, true)
                    .eq(ArticleFav::getIsDeleted, false)
                    .one();
            if(articleFav == null){
                throw new ServiceException("未收藏该文章");
            }
            else{
                new LambdaUpdateChainWrapper<>(articleFavMapper)
                        .eq(ArticleFav::getArticleId, articleId)
                        .eq(ArticleFav::getUserId, userId)
                        .eq(ArticleFav::getIsDeleted, false)
                        .set(ArticleFav::getFavStatus, false)
                        .set(ArticleFav::getUpdateTime, LocalDateTime.now())
                        .update();
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());
            throw new ServiceException("取消收藏失败");
        }
    }


    /**
     * 判断文章是否存在
     * @param articleId 文章ID
     * @return 是否存在
     */
    private Boolean hasArticle(Integer articleId){
        return lambdaQuery().select(Article.class, info -> !info.getColumn().equals("content"))
                .eq(Article::getId, articleId)
                .eq(Article::getDeleted, false)
                .one() != null;
    }

    /**
     * 构建文章返回信息
     * @param article 文章信息
     * @return 文章返回信息
     */
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
