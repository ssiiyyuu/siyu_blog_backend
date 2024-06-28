package com.siyu.service_statistics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2023-09-23 11:55:41
 */
@Getter
@Setter
@ApiModel(value = "Blog对象", description = "")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("博客ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("分类id")
    private String typeId;

    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("博客封面")
    private String cover;

    @ApiModelProperty("博客作者")
    private String author;

    @ApiModelProperty("博客简介")
    private String description;

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

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("更新时间")
    private LocalDateTime gmtUpdate;
}
