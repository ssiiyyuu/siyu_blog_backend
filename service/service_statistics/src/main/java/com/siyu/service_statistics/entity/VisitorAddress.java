package com.siyu.service_statistics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2023-09-23 11:16:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("visitor_address")
@ApiModel(value = "VisitorAddress对象", description = "")
public class VisitorAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("地点")
    @TableId(value = "address", type = IdType.ASSIGN_ID)
    private String address;

    @ApiModelProperty("每日独立访客量")
    private Integer uv;
}
