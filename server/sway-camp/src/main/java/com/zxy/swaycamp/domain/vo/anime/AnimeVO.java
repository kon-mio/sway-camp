package com.zxy.swaycamp.domain.vo.anime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author XinYuan Zhao
 * @since 2023/3/6
 */
@Data
public class AnimeVO {

    private Integer id;

    private String name;

    private String originalName;

    private String region;

    private String cover;

    private String introduction;

    private String officialWebsite;

    private LocalDateTime broadcastTime;


    private List<String> labels;


}
