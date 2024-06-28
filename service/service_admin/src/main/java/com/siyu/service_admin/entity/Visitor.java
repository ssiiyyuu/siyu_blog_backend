package com.siyu.service_admin.entity;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author siyu
 * @since 2023-09-22 04:34:30
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Visitor对象", description = "")
public class Visitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("访客ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("访客identifier")
    @ColumnWidth(20)    
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
    @ColumnWidth(20)    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("更新时间")
    @ColumnWidth(20)    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtUpdate;
}
