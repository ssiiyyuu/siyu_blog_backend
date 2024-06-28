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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.siyu.common_static.enums.OperatorLoggerEnum;
import com.siyu.common_utils.RedisUtil.RedisUtil;
import com.siyu.service_admin.annotation.OperatorLogger;
import com.siyu.service_admin.entity.Blog;
import com.siyu.service_admin.entity.dto.BlogDto;
import com.siyu.service_admin.entity.dto.BlogExcel;
import com.siyu.service_admin.entity.dto.BlogQueryDto;
import com.siyu.service_admin.entity.vo.BlogInfoVo;
import com.siyu.service_admin.entity.vo.BlogPublishVo;
import com.siyu.service_admin.entity.vo.BlogVo;
import com.siyu.service_admin.service.BlogService;
import com.siyu.service_base.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="博客信息管理")
@RestController
@RequestMapping("/service_admin/blog")
public class BlogController {
    
    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisUtil redisUtil;
    
    @PostMapping
    @OperatorLogger(OperatorLoggerEnum.EDIT_BLOG)
    public Result saveOrUpdateBlog(@RequestBody BlogDto blogDto, @RequestParam(required = false) String token) {
        String id = blogService.saveOrUpdateBlog(blogDto, token);
        redisUtil.clearBlogCache();
        return StringUtils.isEmpty(id) ? Result.fail() : Result.ok().data("id", id);
    }

    @GetMapping("/info/{id}")
    public Result getBlogInfoById(@PathVariable String id) {
        BlogInfoVo blogInfo = blogService.getBlogInfoVoById(id);
        return Result.ok().data("blog", blogInfo);
    }

    @GetMapping("/content/{id}")
    public Result getBlogContentById(@PathVariable String id) {
        String content = blogService.getBlogContentVoById(id);
        return Result.ok().data("content", content);
    }

    @GetMapping("/publish/{id}")
    public Result getBlogPublishVoById(@PathVariable String id) {
        BlogPublishVo blogPublishVo = blogService.getBlogPublishVoById(id);
        return Result.ok().data("blog", blogPublishVo);
    }

    @DeleteMapping("/{id}")
    @OperatorLogger(OperatorLoggerEnum.DELETE_BLOG)
    public Result deleteBlogById(@PathVariable String id) {
        boolean flag = blogService.deleteBlogById(id);
        redisUtil.clearBlogCache();
        return flag ? Result.ok() : Result.fail().message("删除失败");
    }

    @DeleteMapping("/list/{ids}")
    @OperatorLogger(OperatorLoggerEnum.DELETE_BLOG)
    public Result deleteBlogByIds(@PathVariable String ids) {
        boolean flag = blogService.deleteBlogByIds(Arrays.asList(ids.split(",")));
        redisUtil.clearBlogCache();
        return flag ? Result.ok() : Result.fail().message("删除失败");
    }

    @GetMapping("/list/{pageNum}/{pageSize}")
    public Result getBlogPage(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<BlogVo> blogPage = blogService.getBlogPage(pageNum, pageSize);
        return Result.ok().data("curPage", blogPage.getCurrent())
                          .data("total", blogPage.getTotal())
                          .data("size", blogPage.getSize())
                          .data("records", blogPage.getRecords());
    }
    
    @GetMapping("/list/query/{pageNum}/{pageSize}")
    public Result getBlogPageByQuery (BlogQueryDto query, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<BlogVo> blogPage = blogService.getBlogPageByQuery(query, pageNum, pageSize);
        return Result.ok().data("curPage", blogPage.getCurrent())
                          .data("total", blogPage.getTotal())
                          .data("size", blogPage.getSize())
                          .data("records", blogPage.getRecords());
    }

    @PutMapping("/{id}")
    public Result updateBlogPublishedById(@PathVariable String id, @RequestParam Boolean flag) {
        boolean res = blogService.updateById(Blog.builder().id(id).published(flag).build());
        redisUtil.clearBlogCache();
        return res ? Result.ok() : Result.fail();
    }

    @ApiOperation(value = "导出Excel(别用swagger调试)")
    @OperatorLogger(OperatorLoggerEnum.EXCEL)
    @PostMapping("/excel")
    public void download(HttpServletResponse response) throws IOException {
        List<BlogExcel> excel = blogService.getBlogExcel();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("BLOG", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), BlogExcel.class)
                 .sheet("博客列表")
                 .doWrite(excel);
    }

}
