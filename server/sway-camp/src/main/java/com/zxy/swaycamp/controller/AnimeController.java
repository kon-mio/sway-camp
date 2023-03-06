package com.zxy.swaycamp.controller;


import com.zxy.swaycamp.annotation.LoginCheck;
import com.zxy.swaycamp.domain.dto.anime.AnimeDTO;
import com.zxy.swaycamp.domain.dto.article.ArticleDTO;
import com.zxy.swaycamp.service.AnimeService;
import com.zxy.swaycamp.utils.request.SwayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 查询动漫信息
      * @param animeId 动漫ID
     * @return 动漫信息
     */
    @GetMapping("/{id}")
    public SwayResult getAnime(@PathVariable("id") Integer animeId){
        return SwayResult.success(animeService.getAnime(animeId));
    }


    /**
     * 上传动漫信息
     * @param animeDTO 文章信息
     * @return null
     */
    @LoginCheck
    @PostMapping("/save")
    public SwayResult saveAnime(@Validated AnimeDTO animeDTO){
        animeService.saveAnime(animeDTO);
        return SwayResult.success();
    }

    /**
     * 上传推荐动漫信息
     * @param animeId 动漫ID
     * @param labelName 标签昵称
     */
    @PostMapping("/recommend/save")
    public SwayResult saveRecommendAnime(@RequestParam Integer animeId,
                                         @RequestParam String labelName){
        animeService.saveRecommendAnime(animeId, labelName);
        return SwayResult.success();
    }

    /**
     * 上传推荐动漫信息
     * @param recommendId 推荐ID
     */
    @PostMapping("/recommend/remove")
    public SwayResult removeRecommendAnime(@RequestParam Integer recommendId){
        animeService.removeRecommendAnime(recommendId);
        return SwayResult.success();
    }


    /**
     * 查询推荐动漫列表
     * @param label 推荐动漫
     * @return 推荐动漫列表
     */
    @GetMapping("/recommend/list")
    public SwayResult listRecommendAnime(@RequestParam(required = false) String label){
        return SwayResult.success(animeService.listRecommendAnime(label));
    }




}

