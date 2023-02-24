package com.zxy.swaycamp.controller;


import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.domain.dto.article.ArticleDTO;
import com.zxy.swaycamp.service.ArticleService;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public SwayResult uploadArticle(@PathVariable Integer id){
        return SwayResult.success(articleService.getArticle(1));
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

