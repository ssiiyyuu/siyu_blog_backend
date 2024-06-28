package com.siyu.service_blog.entity.vo;

import java.time.LocalDateTime;
import java.util.List;

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
@ApiModel(value = "CommentVo对象", description = "")
public class CommentVo {

    @ApiModelProperty("评论id")
    private String id;

    @ApiModelProperty("父评论id -1为根评论")
    private String parentId;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("头像")
    private String avatar;
        
    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("管理员评论")
    private Boolean isAdmin;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("子评论")
    private List<CommentVo> childs;
    
}
