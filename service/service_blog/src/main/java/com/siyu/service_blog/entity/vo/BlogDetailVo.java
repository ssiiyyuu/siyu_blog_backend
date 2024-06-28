package com.siyu.service_blog.entity.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.siyu.service_blog.entity.Tag;
import com.siyu.service_blog.entity.Type;

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
@ApiModel(value = "BlogDetailVo", description = "前台展示博客详情信息VO")
public class BlogDetailVo {
    @ApiModelProperty("博客ID")
    private String id;
    
    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("博客作者")
    private String author;

    @ApiModelProperty("博客封面")
    private String cover;

    @ApiModelProperty("博客正文")
    private String content;

    @ApiModelProperty("浏览数")
    private Integer view;

    @ApiModelProperty("是否可评论")
    private Boolean commentable;

    @ApiModelProperty("分类")
    private Type type;

    @ApiModelProperty("标签")
    private List<Tag> tags;

    @ApiModelProperty("更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtUpdate;
}
