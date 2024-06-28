package com.siyu.service_admin.entity.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.siyu.service_admin.entity.Tag;
import com.siyu.service_admin.entity.Type;

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
@ApiModel(value = "BlogPublishVo对象", description = "")
public class BlogPublishVo {

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

    @ApiModelProperty("分类")
    private Type type;

    @ApiModelProperty("标签")
    private List<Tag> tags;

    @ApiModelProperty("博客创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("博客更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtUpdate;
}
