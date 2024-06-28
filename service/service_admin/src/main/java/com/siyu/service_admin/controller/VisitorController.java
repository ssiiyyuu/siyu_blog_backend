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
import com.siyu.service_admin.entity.Visitor;
import com.siyu.service_admin.entity.dto.LogQueryDto;
import com.siyu.service_admin.service.VisitorService;
import com.siyu.service_base.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="访客信息管理")
@RestController
@RequestMapping("/service_admin/visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping("/list/{pageNum}/{pageSize}")
    public Result getVisitorPage(@PathVariable int pageNum, @PathVariable int pageSize) {
        Page<Visitor> visitorPage = visitorService.getVisitorPage(pageNum, pageSize);
        return Result.ok().data("curPage", visitorPage.getCurrent())
                          .data("total", visitorPage.getTotal())
                          .data("size", visitorPage.getSize())
                          .data("records", visitorPage.getRecords());
    }
    
    @GetMapping("/list/query/{pageNum}/{pageSize}")
    public Result getVisitorPageByQuery (LogQueryDto query, @PathVariable int pageNum, @PathVariable int pageSize) {
        Page<Visitor> visitorPage = visitorService.getVisitorPageByQuery(query, pageNum, pageSize);
        return Result.ok().data("curPage", visitorPage.getCurrent())
                          .data("total", visitorPage.getTotal())
                          .data("size", visitorPage.getSize())
                          .data("records", visitorPage.getRecords());
    }

    @ApiOperation(value = "导出Excel(别用swagger调试)")
    @OperatorLogger(OperatorLoggerEnum.EXCEL)
    @PostMapping("/excel")
    public void download(HttpServletResponse response) throws IOException {
        List<Visitor> excel = visitorService.list();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("VISITOR", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Visitor.class)
                 .sheet("游客列表")
                 .doWrite(excel);
    }
}
