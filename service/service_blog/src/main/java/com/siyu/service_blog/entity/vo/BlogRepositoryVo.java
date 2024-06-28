package com.siyu.service_blog.entity.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BlogRepositoryVo", description = "前台展示博客总体信息VO")
public class BlogRepositoryVo {
    @ApiModelProperty("博客ID")
    private String id;
    
    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("博客作者")
    private String author;
    
    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
}
