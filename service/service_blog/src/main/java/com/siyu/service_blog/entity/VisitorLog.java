package com.siyu.service_blog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author siyu
 * @since 2023-09-13 11:42:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("visitor_log")
@ApiModel(value = "VisitorLog对象", description = "")
public class VisitorLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("访客日志表ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("访客identifier")
    private String uuid;

    @ApiModelProperty("请求接口")
    private String uri;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("请求参数")
    private String params;

    @ApiModelProperty("访客行为")
    private String behavior;

    @ApiModelProperty("访问内容")
    private String content;

    @ApiModelProperty("请求时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
}
