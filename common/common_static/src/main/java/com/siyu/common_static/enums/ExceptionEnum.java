package com.siyu.common_static.enums;

import com.siyu.common_static.constant.ResultCodeConstant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionEnum {

    ERROR(ResultCodeConstant.ERROR, "请求错误"),

    EXPIRE_TOKEN(ResultCodeConstant.TOKEN_EXPIRED, "token已过期"),
    
    ILLEGAL_TOKEN(ResultCodeConstant.ILLEGAL_TOKEN, "非法token"),

    TOO_MANY_REQUEST(ResultCodeConstant.TOO_MANY_REQUEST, "请求次数超过限制"),

    ILLEGAL_PARAM(ResultCodeConstant.ILLEAGAL_PARAM, "请求参数非法"),

    MISSING_PARAM(ResultCodeConstant.MISSING_PARAM, "请求参数不足"),

    NOT_FOUND(ResultCodeConstant.NOT_FOUND, "未查询到内容或内容已丢失"),

    NO_PERMISSION(ResultCodeConstant.NO_PERMISSION, "你没有权限进行该操作"), 
    
    UPLOAD_FAILURE(ResultCodeConstant.FAILURE, "上传失败")
    ;

    private Integer code;
    private String message;

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
