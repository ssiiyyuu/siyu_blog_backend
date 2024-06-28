package com.siyu.service_statistics.entity.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2023-09-23 09:29:05
 */
@Getter
@Setter
@ApiModel(value = "VisitorLogDto对象", description = "")
public class VisitorLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("访客identifier")
    private String uuid;

    @ApiModelProperty("请求时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
}
