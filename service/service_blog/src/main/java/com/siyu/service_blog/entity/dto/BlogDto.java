package com.siyu.service_blog.entity.dto;

import java.time.LocalDateTime;
import java.util.List;

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
@ApiModel(value = "BlogDto对象", description = "")
public class BlogDto {
    
    @ApiModelProperty("博客ID")
    private String id;

    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("博客作者")
    private String author;

    @ApiModelProperty("博客简介")
    private String description;
    
    @ApiModelProperty("博客封面")
    private String cover;
        
    @ApiModelProperty("博客正文")
    private String content;

    @ApiModelProperty("浏览数")
    private Integer view;

    @ApiModelProperty("是否可评论")
    private Boolean commentable;

    @ApiModelProperty("推荐指数")
    private Integer sort;

    @ApiModelProperty("是否发布")
    private Boolean published;

    @ApiModelProperty("分类")
    private Type type;

    @ApiModelProperty("标签")
    private List<Tag> tags;

    @ApiModelProperty("博客创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("博客更新时间")
    private LocalDateTime gmtUpdate;
}
