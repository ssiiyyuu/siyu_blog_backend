package com.siyu.service_admin.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

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
 * @since 2023-09-20 02:36:29
 */
@Getter
@Setter
@ApiModel(value = "BlogVo对象", description = "")
public class BlogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("博客ID")
    private String id;

    @ApiModelProperty("博客标题")
    private String title;

    @ApiModelProperty("博客作者")
    private String author;

    @ApiModelProperty("是否可评论")
    private Boolean commentable;

    @ApiModelProperty("推荐指数")
    private Integer sort;

    @ApiModelProperty("是否发布")
    private Boolean published;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtUpdate;
}
