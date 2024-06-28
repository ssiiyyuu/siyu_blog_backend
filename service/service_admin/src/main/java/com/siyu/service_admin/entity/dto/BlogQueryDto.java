package com.siyu.service_admin.entity.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "BlogQueryDto对象", description = "")
public class BlogQueryDto implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标题")
    private String title;
    
    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("状态")
    private Boolean published;
    
    @ApiModelProperty("起始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime begin;

    @ApiModelProperty("终止时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
}
