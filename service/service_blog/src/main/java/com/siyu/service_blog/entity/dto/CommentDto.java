package com.siyu.service_blog.entity.dto;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

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
@ApiModel(value = "CommentDto对象", description = "")
public class CommentDto {

    @ApiModelProperty("博客id")
    private String blogId;

    @ApiModelProperty("父评论id -1为根评论")
    private String parentId;

    @ApiModelProperty("昵称")
    private String name;
        
    @ApiModelProperty("评论内容")
    private String content;

    // 校检
    public Boolean valid() {
        return !StringUtils.isEmpty(name) && !StringUtils.isEmpty(content);
    }
}
