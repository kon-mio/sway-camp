package com.zxy.swaycamp.controller;


import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.domain.dto.article.ArticleDTO;
import com.zxy.swaycamp.domain.vo.ArticleVO;
import com.zxy.swaycamp.domain.vo.PageVO;
import com.zxy.swaycamp.service.ArticleService;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private ArticleService articleService;

    /**
     * 根据ID查询文章信息
     * @param id 文章id
     * @return 文章信息
     */
    @GetMapping("/{id}")
    public SwayResult uploadArticle(@PathVariable Integer id){
        return SwayResult.success(articleService.getArticle(1));
    }

    /**
     * 分页查询文章列表
     * @param index 索引
     * @param size 大小
     * @return listArticle
     */
    @GetMapping("/list")
    public SwayResult<PageVO<ArticleVO> > listArticle(@RequestParam Integer index, @RequestParam Integer size){
        return SwayResult.success(articleService.listArticle(index, size));
    }

    /**
     * 上传文章接口
     * @param articleDTO 文章信息
     * @return null
     */
    @LoginCheck
    @PostMapping("/upload")
    public SwayResult uploadArticle(@Validated ArticleDTO articleDTO){
        articleService.uploadArticle(articleDTO);
        return SwayResult.success();
    }

}

