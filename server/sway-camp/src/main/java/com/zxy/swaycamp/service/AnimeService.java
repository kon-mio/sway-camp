package com.zxy.swaycamp.service;

import com.zxy.swaycamp.domain.dto.anime.AnimeDTO;
import com.zxy.swaycamp.domain.entity.Anime;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 动漫信息表 服务类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-03-06
 */
public interface AnimeService extends IService<Anime> {


    /**
     * 新增动漫
     * @param animeDTO 动漫信息
     */
    void saveAnime(AnimeDTO animeDTO);

}
