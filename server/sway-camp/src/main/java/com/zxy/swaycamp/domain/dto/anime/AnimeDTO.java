package com.zxy.swaycamp.domain.dto.anime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author XinYuan Zhao
 * @since 2023/3/6
 */
@Data
public class AnimeDTO {

    @NotEmpty
    private String name;
    @NotEmpty
    private String originalName;
    @NotEmpty
    private String region;
    @NotEmpty
    private String introduction;
    @NotEmpty
    private String officialWebsite;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime broadcastTime;

    private MultipartFile cover;
    @NotEmpty
    private List<String> labels;
}
