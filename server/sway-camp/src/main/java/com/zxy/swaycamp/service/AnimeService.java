package com.zxy.swaycamp.service;

import com.zxy.swaycamp.domain.dto.anime.AnimeDTO;
import com.zxy.swaycamp.domain.entity.Anime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.swaycamp.domain.vo.anime.AnimeVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * 获取动漫信息
     * @param animeId 动漫ID
     * @return 动漫信息
     */
    AnimeVO getAnime(Integer animeId);

    /**
     * 新增动漫
     * @param animeDTO 动漫信息
     */
    void saveAnime(AnimeDTO animeDTO);

    /**
     * 新增推荐动漫
     * @param animeId 动漫ID
     * @param labelName 推荐标签
     */
    void saveRecommendAnime(Integer animeId, String labelName);

    /**
     * 取消推荐动漫
     * @param recommendId 推荐ID
     */
    void removeRecommendAnime(Integer recommendId);

    /**
     * 查询推荐动漫列表
     * @param label 推荐标签
     * @return 查询推荐动漫列表
     */
    List<AnimeVO> listRecommendAnime(String label);

    /**
     * 上传动漫图片
     * @param files 图片文件
     * @param animeId 动漫ID
     */
    void uploadAnimeImage(MultipartFile[] files, Integer animeId);


}
