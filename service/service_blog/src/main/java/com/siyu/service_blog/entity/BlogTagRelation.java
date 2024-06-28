package com.siyu.service_blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2023-09-11 10:31:08
 */
@Getter
@Setter
@TableName("blog_tag_relation")
@ApiModel(value = "BlogTagRelation对象", description = "")
public class BlogTagRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("博客ID")
    private String blogId;

    @ApiModelProperty("标签ID")
    private String tagId;
}
