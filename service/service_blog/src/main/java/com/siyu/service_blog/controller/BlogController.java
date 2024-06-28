package com.siyu.service_blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.siyu.common_static.enums.VisitorLoggerEnum;
import com.siyu.service_base.result.Result;
import com.siyu.service_blog.annotation.VisitLogger;
import com.siyu.service_blog.entity.vo.BlogDetailVo;
import com.siyu.service_blog.entity.vo.BlogInfoVo;
import com.siyu.service_blog.service.BlogService;

import io.swagger.annotations.Api;

@Api(description="博客信息管理")
@RestController
@RequestMapping("/service_blog/blog")
public class BlogController {

    private static Integer PAGE_SIZE = 4;

    @Autowired
    private BlogService blogService;

    @VisitLogger(VisitorLoggerEnum.BLOG)
    @GetMapping("/{id}")
    public Result getBlogById(@PathVariable String id) throws Exception {
        BlogDetailVo blog = blogService.getBlogDetailVoById(id);
        return Result.ok().data("blog", blog);
    }

    @GetMapping("/list")
    public Result getBlogs(@RequestParam(value = "pageNum") int pageNum) {
        PageInfo<BlogInfoVo> pageBlogInfo = blogService.getBlogInfoVoList(pageNum, PAGE_SIZE);
        return Result.ok().data("records", pageBlogInfo.getList())
                          .data("curPage", pageBlogInfo.getPageNum())
                          .data("total", pageBlogInfo.getTotal());
    }

    @VisitLogger(VisitorLoggerEnum.SEARCH)
    @GetMapping("/list/query")
    public Result getBlogsByQuery(@RequestParam(value = "pageNum") int pageNum,
                                  @RequestParam(value = "query", required = false) String query) {
        PageInfo<BlogInfoVo> pageBlogInfo = blogService.getBlogInfoVoListByQuery(query, pageNum, PAGE_SIZE);
        return Result.ok().data("records", pageBlogInfo.getList())
                          .data("curPage", pageBlogInfo.getPageNum())
                          .data("total", pageBlogInfo.getTotal());
    }

    @VisitLogger(VisitorLoggerEnum.TAG)
    @GetMapping("/tag/{title}")
    public Result getBlogsByTag(@PathVariable String title,
                                @RequestParam(value = "pageNum")  int pageNum) {
        PageInfo<BlogInfoVo> pageBlogInfo = blogService.getBlogInfoVoListByTagTitle(title, pageNum, PAGE_SIZE);
        return Result.ok().data("records", pageBlogInfo.getList())
                          .data("curPage", pageBlogInfo.getPageNum())
                          .data("total", pageBlogInfo.getTotal());
    }

    @VisitLogger(VisitorLoggerEnum.TYPE)
    @GetMapping("/type/{title}")
    public Result getBlogsByType(@PathVariable String title,
                                 @RequestParam(value = "pageNum")  int pageNum) {
        PageInfo<BlogInfoVo> pageBlogInfo = blogService.getBlogInfoVoListByTypeTitle(title, pageNum, PAGE_SIZE);
        return Result.ok().data("records", pageBlogInfo.getList())
                          .data("curPage", pageBlogInfo.getPageNum())
                          .data("total", pageBlogInfo.getTotal());
    }

    @VisitLogger(VisitorLoggerEnum.REPOSITORY)
    @GetMapping("/repository")
    public Result getBlogRepository() {
        return Result.ok().data("repository", blogService.getRepository());
    }
}
