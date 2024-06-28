package com.siyu.service_statistics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-09-23 10:03:32
 */
@Data
@Builder
@TableName("visit_per_day")
@ApiModel(value = "VisitPerDay对象", description = "")
public class VisitPerDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("每日pv")
    private Integer pv;

    @ApiModelProperty("每日uv")
    private Integer uv;

    @ApiModelProperty("日期")
    @JsonFormat(timezone = "GMT+8", pattern = "MM-dd")
    private LocalDateTime date;
}
