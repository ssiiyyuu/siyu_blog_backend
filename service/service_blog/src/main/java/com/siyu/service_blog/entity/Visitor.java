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
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author siyu
 * @since 2023-09-11 08:27:20
 */
@Data
@Builder
@TableName("visitor")
@ApiModel(value = "Visitor对象", description = "")
public class Visitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("访客ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("访客Identifier")
    private String uuid;

    @ApiModelProperty("访客IP")
    private String ip;

    @ApiModelProperty("访客地址")
    private String ipAddress;

    @ApiModelProperty("访客os")
    private String os;

    @ApiModelProperty("访客设备")
    private String device;

    @ApiModelProperty("访客浏览器")
    private String browser;

    @ApiModelProperty("访客访问次数")
    private Integer view;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtUpdate;

}
