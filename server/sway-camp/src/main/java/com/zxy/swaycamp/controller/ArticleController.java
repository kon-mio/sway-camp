package com.zxy.swaycamp.controller;


import com.zxy.swaycamp.annotation.Log;
import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.common.enums.Action;
import com.zxy.swaycamp.domain.dto.article.ArticleDTO;
import com.zxy.swaycamp.domain.dto.article.SearchDTO;
import com.zxy.swaycamp.domain.entity.ArticleSort;
import com.zxy.swaycamp.domain.vo.article.ArticleLabelVO;
import com.zxy.swaycamp.domain.vo.article.ArticleSortVO;
import com.zxy.swaycamp.domain.vo.article.ArticleVO;
import com.zxy.swaycamp.domain.vo.PageVO;
import com.zxy.swaycamp.service.ArticleService;
import com.zxy.swaycamp.utils.query.CommonQuery;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-24
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private CommonQuery commonQuery;
    @Autowired
    private ArticleService articleService;

    /**
     * 根据ID查询文章信息
     * @param id 文章id
     * @return 文章信息
     */
    @Log(title = "查询文章", action = Action.SELECT)
    @GetMapping("/{id}")
    public SwayResult uploadArticle(@PathVariable Integer id){
        return SwayResult.success(articleService.getArticle(id));
    }

    /**
     * 分页查询文章列表
     * @param index 索引
     * @param size 大小
     * @return listArticle
     */
    @Log(title = "分页查询文章", action = Action.SELECT)
    @GetMapping("/list")
    public SwayResult<PageVO<ArticleVO> > listArticle(@RequestParam Integer index, @RequestParam Integer size){
        return SwayResult.success(articleService.listArticle(index, size));
    }

    /**
     * 分页查询收藏文章列表
     * @param index 索引
     * @param size 大小
     * @return listArticle
     */
    @Log(title = "查询收藏文章", action = Action.SELECT)
    @LoginCheck
    @GetMapping("/fav/list")
    public SwayResult<PageVO<ArticleVO> > listFavArticle(@RequestParam Integer index, @RequestParam Integer size){
        return SwayResult.success(articleService.listFavArticle(index, size));
    }

    /**
     * 分页查询文章列表
     * @param searchDTO 检索信息
     * @return listArticle
     */
    @Log(title = "查询文章列表", action = Action.SELECT)
    @PostMapping("/list/search")
    public SwayResult<List<ArticleVO> > listSearchArticle(@RequestBody @Validated SearchDTO searchDTO){
        return SwayResult.success(articleService.listSearchArticle(searchDTO));
    }
    /**
     * 查询推荐文章列表
     * @param size 大小
     * @return listArticle
     */
    @Log(title = "查询推荐文章", action = Action.SELECT)
    @GetMapping("/list/recommend")
    public SwayResult<List<ArticleVO> > listRecommend(@RequestParam Integer size){
        return SwayResult.success(articleService.listRecommend(size));
    }

    /**
     * 上传文章接口
     * @param articleDTO 文章信息
     * @return null
     */
    @LoginCheck
    @Log(title = "上传文章", action = Action.INSERT)
    @PostMapping("/upload")
    public SwayResult uploadArticle(@Validated ArticleDTO articleDTO){
        articleService.saveArticle(articleDTO);
        return SwayResult.success();
    }

    /**
     * 收藏文章
     * @param articleId 文章
     * @return null
     */
    @LoginCheck
    @Log(title = "收藏文章", action = Action.INSERT)
    @PostMapping("/fav/save")
    public SwayResult savFav(@RequestParam Integer articleId){
        articleService.saveArticleFav(articleId);
        return SwayResult.success();
    }

    /**
     * 移除收藏文章
     * @param articleId 文章
     * @return null
     */
    @LoginCheck
    @Log(title = "移除收藏", action = Action.DELETE)
    @PostMapping("/fav/remove")
    public SwayResult removeFav(@RequestParam Integer articleId){
        articleService.removeArticleFav(articleId);
        return SwayResult.success();
    }




    /**
     * 获取文章标签列表
     * @return 章标签列表
     */
    @Log(title = "获取文章标签", action = Action.SELECT)
    @GetMapping("/sort")
    public SwayResult getSorts(){
        List<ArticleSort> articleSorts = commonQuery.getSortInfo();
        List<ArticleSortVO> articleSortVOS = articleSorts.stream().map(item ->{
            ArticleSortVO articleSortVO = new ArticleSortVO();
            articleSortVO.setId(item.getId());
            articleSortVO.setSortName(item.getSortName());
            articleSortVO.setSortDescription(item.getSortDescription());
            articleSortVO.setArticleCount(item.getCountOfSort());
            if(item.getLabels() != null){
                List<ArticleLabelVO> articleLabelVOS =  item.getLabels().stream().map(label->{
                    ArticleLabelVO articleLabelVO = new ArticleLabelVO();
                    articleLabelVO.setId(label.getId());
                    articleLabelVO.setLabelName(label.getLabelName());
                    articleLabelVO.setSortId(label.getSortId());
                    articleLabelVO.setArticleCount(label.getCountOfLabel());
                    articleLabelVO.setLabelDescription(label.getLabelDescription());
                    return articleLabelVO;
                }).collect(Collectors.toList());
                articleSortVO.setLabels(articleLabelVOS);
            }
            return articleSortVO;
        }).collect(Collectors.toList());
        return SwayResult.success(articleSortVOS);
    }

}

