package com.siyu.service_admin.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siyu.common_static.enums.OperatorLoggerEnum;
import com.siyu.service_admin.annotation.OperatorLogger;
import com.siyu.service_admin.entity.OperatorLog;
import com.siyu.service_admin.entity.VisitorLog;
import com.siyu.service_admin.entity.dto.LogQueryDto;
import com.siyu.service_admin.service.OperatorLogService;
import com.siyu.service_admin.service.VisitorLogService;
import com.siyu.service_base.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="日志信息管理")
@RestController
@RequestMapping("/service_admin/log")
public class LogController {

    @Autowired
    private VisitorLogService visitorlogService;

    @Autowired
    private OperatorLogService operatorLogService;

    @GetMapping("visitor/list/{pageNum}/{pageSize}")
    public Result getVisitorLogPage(@PathVariable int pageNum, @PathVariable int pageSize) {
        Page<VisitorLog> visitorlogPage = visitorlogService.getVisitorLogPage(pageNum, pageSize);
        return Result.ok().data("curPage", visitorlogPage.getCurrent())
                          .data("total", visitorlogPage.getTotal())
                          .data("size", visitorlogPage.getSize())
                          .data("records", visitorlogPage.getRecords());
    }
    
    @GetMapping("visitor/list/query/{pageNum}/{pageSize}")
    public Result getVisitorLogPageByQuery (LogQueryDto query, @PathVariable int pageNum, @PathVariable int pageSize) {
        Page<VisitorLog> visitorlogPage = visitorlogService.getVisitorLogPageByQuery(query, pageNum, pageSize);
        return Result.ok().data("curPage", visitorlogPage.getCurrent())
                          .data("total", visitorlogPage.getTotal())
                          .data("size", visitorlogPage.getSize())
                          .data("records", visitorlogPage.getRecords());
    }
        
    @GetMapping("operator/list/{pageNum}/{pageSize}")
    public Result getOperatorLogPage(@PathVariable int pageNum, @PathVariable int pageSize) {
        Page<OperatorLog> operatorlogPage = operatorLogService.getOperatorLogPage(pageNum, pageSize);
        return Result.ok().data("curPage", operatorlogPage.getCurrent())
                          .data("total", operatorlogPage.getTotal())
                          .data("size", operatorlogPage.getSize())
                          .data("records", operatorlogPage.getRecords());
    }
    
    @GetMapping("operator/list/query/{pageNum}/{pageSize}")
    public Result getOperatorLogPageByQuery (LogQueryDto query, @PathVariable int pageNum, @PathVariable int pageSize) {
        Page<OperatorLog> operatorlogPage = operatorLogService.getOperatorLogPageByQuery(query, pageNum, pageSize);
        return Result.ok().data("curPage", operatorlogPage.getCurrent())
                          .data("total", operatorlogPage.getTotal())
                          .data("size", operatorlogPage.getSize())
                          .data("records", operatorlogPage.getRecords());
    }

    @ApiOperation(value = "导出Excel(别用swagger调试)")
    @OperatorLogger(OperatorLoggerEnum.EXCEL)
    @PostMapping("/visitor/excel")
    public void downloadV(HttpServletResponse response) throws IOException {
        List<VisitorLog> excel = visitorlogService.list();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("VISITOR_LOG", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), VisitorLog.class)
                 .sheet("访问日志列表")
                 .doWrite(excel);
    }
    
    @ApiOperation(value = "导出Excel(别用swagger调试)")
    @OperatorLogger(OperatorLoggerEnum.EXCEL)
    @PostMapping("/operator/excel")
    public void downloadO(HttpServletResponse response) throws IOException {
        List<OperatorLog> excel = operatorLogService.list();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("OPERATOR_LOG", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), OperatorLog.class)
                 .sheet("操作日志列表")
                 .doWrite(excel);
    }
}
