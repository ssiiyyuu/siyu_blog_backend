package com.siyu.service_blog.service;

import com.siyu.service_blog.entity.Comment;
import com.siyu.service_blog.entity.dto.CommentDto;
import com.siyu.service_blog.entity.vo.CommentVo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-12 05:15:09
 */
public interface CommentService extends IService<Comment> {
    public PageInfo<CommentVo> getCommentVoPageByBlogId(String id, int pageNUm, int pageSize);

    public String saveComment(CommentDto comment, String token);
}
