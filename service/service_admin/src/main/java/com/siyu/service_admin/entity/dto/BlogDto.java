package com.siyu.service_admin.entity.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "BlogInfoDto对象", description = "")
public class BlogDto implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("博客ID")
    private String id;

    @ApiModelProperty("分类id")
    private String typeId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("是否可评论")
    private Boolean commentable;
    
    @ApiModelProperty("推荐指数")
    private Integer sort;

    @ApiModelProperty("博客简介")
    private String description;

    @ApiModelProperty("标签id")
    private List<String> tagIds;

}
