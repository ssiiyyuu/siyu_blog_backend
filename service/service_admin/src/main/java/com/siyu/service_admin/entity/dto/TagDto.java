package com.siyu.service_admin.entity.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "TagDto对象", description = "")
public class TagDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标签ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("标签标题")
    private String title;

    @ApiModelProperty("博客关联数")
    private Integer count;
}
