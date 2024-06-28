package com.siyu.service_admin.entity.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogExcel implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @ExcelProperty("ID")
    private String id;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("作者")
    private String author;

    @ExcelProperty("封面")
    @ColumnWidth(20)
    private String cover;

    @ExcelProperty("简介")
    @ColumnWidth(30)    
    private String description;

    @ExcelProperty("浏览数")
    private Integer view;

    @ExcelProperty("评论区状态")
    private Boolean commentable;
    
    @ExcelProperty("推荐指数")
    private Integer sort;

    @ExcelProperty("创建时间")
    @ColumnWidth(20)
    private LocalDateTime gmtCreate;

    @ExcelProperty("更新时间")   
    @ColumnWidth(20) 
    private LocalDateTime gmtUpdate;
}
