package com.siyu.service_blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.siyu.service_base.result.Result;
import com.siyu.service_blog.annotation.LimitRequest;
import com.siyu.service_blog.entity.dto.CommentDto;
import com.siyu.service_blog.entity.vo.CommentVo;
import com.siyu.service_blog.service.CommentService;

import io.swagger.annotations.Api;


@Api(description="评论展示管理")
@RestController
@RequestMapping("/service_blog/comment")
public class CommentController {

    private static final Integer PAGE_SIZE = 2;

    @Autowired
    private CommentService commentService;

    @PostMapping
    @LimitRequest(seconds = 30, counts = 1)
    public Result saveComment(@RequestBody CommentDto comment, @RequestParam(required = false) String token) {
        String id = commentService.saveComment(comment, token);
        return Result.ok().data("id", id);
    }

    @GetMapping("/{id}/{pageNum}")
    public Result getCommentByBlogId(@PathVariable String id, 
                                     @PathVariable int pageNum) {
        PageInfo<CommentVo> commentVoPage = commentService.getCommentVoPageByBlogId(id, pageNum, PAGE_SIZE);
        if(commentVoPage == null) {
            return Result.ok().message("COMMENT CLOSED");
        }
        return Result.ok().data("records", commentVoPage.getList())
                          .data("curPage", commentVoPage.getPageNum())
                          .data("total", commentVoPage.getTotal());
    }


}
