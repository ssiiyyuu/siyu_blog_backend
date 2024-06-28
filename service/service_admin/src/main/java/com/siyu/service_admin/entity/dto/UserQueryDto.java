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
@ApiModel(value = "UserQueryDto对象", description = "")
public class UserQueryDto implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    private String name;
    
    @ApiModelProperty("角色权限")
    private String role;

    @ApiModelProperty("状态")
    private Boolean status;

    @ApiModelProperty("起始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime begin;

    @ApiModelProperty("终止时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
}
