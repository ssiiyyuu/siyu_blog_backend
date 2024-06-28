package com.siyu.service_admin.entity.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author siyu
 * @since 2023-09-20 02:36:29
 */
@Getter
@Setter
@ApiModel(value = "BlogInfoVo对象", description = "")
public class BlogInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("博客ID")
    private String id;

    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("是否可评论")
    private Boolean commentable;

    @ApiModelProperty("推荐指数")
    private Integer sort;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("分类id")
    private String typeId;

    @ApiModelProperty("标签id")
    private List<String> tagIds;
}
