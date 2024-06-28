package com.siyu.service_blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author siyu
 * @since 2023-09-15 01:44:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("visitor_address")
@ApiModel(value = "VisitorAddress对象", description = "")
public class VisitorAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("地点")
    private String address;

    @ApiModelProperty("每日独立访客量")
    private Integer uv;
}
