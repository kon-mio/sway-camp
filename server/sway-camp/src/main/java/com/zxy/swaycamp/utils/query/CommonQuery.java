package com.zxy.swaycamp.utils.query;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.zxy.swaycamp.common.constant.CacheConstants;
import com.zxy.swaycamp.common.constant.TimeConst;
import com.zxy.swaycamp.domain.entity.Article;
import com.zxy.swaycamp.domain.entity.ArticleLabel;
import com.zxy.swaycamp.domain.entity.ArticleSort;
import com.zxy.swaycamp.domain.entity.User;
import com.zxy.swaycamp.mapper.ArticleLabelMapper;
import com.zxy.swaycamp.mapper.ArticleMapper;
import com.zxy.swaycamp.mapper.ArticleSortMapper;
import com.zxy.swaycamp.service.UserService;
import com.zxy.swaycamp.utils.redis.RedisCache;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 通用查询
 *
 * @author XinYuan Zhao
 * @since 2023/2/25
 */
@Component
public class CommonQuery {

    @Resource
    private RedisCache redisCache;
    @Resource
    private UserService userService;

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleSortMapper articleSortMapper;

    @Resource
    private ArticleLabelMapper labelMapper;

    public User getUser(Integer userId) {
        User user = (User) redisCache.getCacheObject(CacheConstants.USER_KEY + userId.toString());
        if (user != null) {
            return user;
        }
        User u = userService.getById(userId);
        if (u != null) {
            redisCache.setCacheObject(CacheConstants.USER_KEY + userId.toString(), u, TimeConst.TOKEN_EXPIRE_REFRESH, TimeUnit.DAYS);
            return u;
        }
        return null;
    }

    public List<ArticleSort> getSortInfo() {
        List<ArticleSort> sorts = new LambdaQueryChainWrapper<>(articleSortMapper).list();
        if (!CollectionUtils.isEmpty(sorts)) {
            sorts.forEach(sort -> {
                LambdaQueryChainWrapper<Article> sortWrapper = new LambdaQueryChainWrapper<>(articleMapper);
                Integer countOfSort = sortWrapper.eq(Article::getSortId, sort.getId())
                        .eq(Article::getViewStatus, true).count();
                sort.setCountOfSort(countOfSort);

                LambdaQueryChainWrapper<ArticleLabel> wrapper = new LambdaQueryChainWrapper<>(labelMapper);
                List<ArticleLabel> labels = wrapper.eq(ArticleLabel::getSortId, sort.getId()).list();
                if (!CollectionUtils.isEmpty(labels)) {
                    labels.forEach(label -> {
                        LambdaQueryChainWrapper<Article> labelWrapper = new LambdaQueryChainWrapper<>(articleMapper);
                        Integer countOfLabel = labelWrapper.eq(Article::getLabelId, label.getId())
                                .eq(Article::getViewStatus, true).count();
                        label.setCountOfLabel(countOfLabel);
                    });
                    sort.setLabels(labels);
                }
            });
            return sorts;
        } else {
            return null;
        }
    }
}
