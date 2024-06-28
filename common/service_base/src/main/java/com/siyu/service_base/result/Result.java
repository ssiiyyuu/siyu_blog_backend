package com.siyu.service_base.result;

import java.util.HashMap;
import java.util.Map;

import com.siyu.common_static.constant.ResultCodeConstant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Result {
    
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private Result() {

    }

    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }
        
    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
    
    public static Result createUnsuccessResult() {
        return new Result().success(false);
    }

    public static Result ok() {
        Result result = new Result().success(true)
                                    .code(ResultCodeConstant.SUCCESS)
                                    .message("请求成功");
        return result;
    }
    
    public static Result fail() {
        Result result = new Result().success(false)
                                    .code(ResultCodeConstant.FAILURE)
                                    .message("请求失败");
        return result;
    }

    public static Result error() {
        Result result = new Result().success(false)
                                    .code(ResultCodeConstant.ERROR)
                                    .message("请求错误");
        return result;
    }
}