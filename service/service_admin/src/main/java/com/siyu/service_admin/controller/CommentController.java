package com.siyu.service_admin.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.siyu.common_static.enums.OperatorLoggerEnum;
import com.siyu.service_admin.annotation.OperatorLogger;
import com.siyu.service_admin.entity.Comment;
import com.siyu.service_admin.service.CommentService;
import com.siyu.service_base.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="评论信息管理")
@RestController
@RequestMapping("/service_admin/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    

    @DeleteMapping("/{id}")
    @OperatorLogger(OperatorLoggerEnum.DELETE_COMMENT)
    public Result deleteCommentById(@PathVariable String id) {
        return Result.ok().data("count", commentService.deleteCommentById(id));
    }

    @DeleteMapping("/list/{ids}")
    @OperatorLogger(OperatorLoggerEnum.DELETE_COMMENT)
    public Result deleteCommentByIds(@PathVariable String ids) {
        return Result.ok().data("count", commentService.deleteCommentByIds(Arrays.asList(ids.split(","))));
    }


    @GetMapping("/list/{pageNum}/{pageSize}")
    public Result getCommentPage(@PathVariable int pageNum, @PathVariable int pageSize) {
        PageInfo<Comment> commentPageInfo = commentService.getCommentPageInfo(pageNum, pageSize);
        return Result.ok().data("curPage", commentPageInfo.getPageNum())
                          .data("total", commentPageInfo.getTotal())
                          .data("size", commentPageInfo.getPageSize())
                          .data("records", commentPageInfo.getList());
    }
    
    @GetMapping("/list/query/{pageNum}/{pageSize}")
    public Result getCommentPageByQuery (@RequestParam String query, @PathVariable int pageNum, @PathVariable int pageSize) {
        PageInfo<Comment> commentPageInfo = commentService.getCommentPageInfoByQuery(query, pageNum, pageSize);
        return Result.ok().data("curPage", commentPageInfo.getPageNum())
                          .data("total", commentPageInfo.getTotal())
                          .data("size",  commentPageInfo.getPageSize())
                          .data("records", commentPageInfo.getList());
    }
        
    @ApiOperation(value = "导出Excel(别用swagger调试)")
    @OperatorLogger(OperatorLoggerEnum.EXCEL)
    @PostMapping("/excel")
    public void download(HttpServletResponse response) throws IOException {
        List<Comment> excel = commentService.list();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("COMMENT", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Comment.class)
                 .sheet("评论列表")
                 .doWrite(excel);
    }
}
