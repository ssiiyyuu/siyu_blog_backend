package com.siyu.service_admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author siyu
 * @since 2023-09-20 12:33:19
 */
@Getter
@Setter
@AllArgsConstructor
@TableName("blog_tag_relation")
@ApiModel(value = "BlogTagRelation对象", description = "")
public class BlogTagRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("博客ID")
    private String blogId;

    @ApiModelProperty("标签ID")
    private String tagId;
}
