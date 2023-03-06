package com.zxy.swaycamp.controller;


import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.domain.dto.anime.AnimeDTO;
import com.zxy.swaycamp.domain.dto.article.ArticleDTO;
import com.zxy.swaycamp.service.AnimeService;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 动漫信息表 前端控制器
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-03-06
 */
@RestController
@RequestMapping("/api/anime")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    /**
     * 上传动漫信息
     * @param animeDTO 文章信息
     * @return null
     */
    @LoginCheck
    @PostMapping("/save")
    public SwayResult uploadArticle(@Validated AnimeDTO animeDTO){
        animeService.saveAnime(animeDTO);
        return SwayResult.success();
    }

}

