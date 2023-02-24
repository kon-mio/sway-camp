package com.zxy.swaycamp.service.impl;

import com.zxy.swaycamp.common.enums.CodeMsg;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.article.ArticleDTO;
import com.zxy.swaycamp.domain.entity.Article;
import com.zxy.swaycamp.domain.vo.ArticleVO;
import com.zxy.swaycamp.domain.vo.FileVO;
import com.zxy.swaycamp.mapper.ArticleMapper;
import com.zxy.swaycamp.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.service.OssService;
import com.zxy.swaycamp.utils.SwayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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


    /**
     * 根据Id查询文章
     * @param articleId 文章ID
     * @return 文章详细信息
     */
    public ArticleVO getArticle(Integer articleId){
        Article article = lambdaQuery().eq(Article::getId, articleId).one();
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        return articleVO;
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
}
