package com.siyu.service_admin.service;

import com.siyu.service_admin.entity.Comment;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-24 12:40:08
 */
public interface CommentService extends IService<Comment> {

    PageInfo<Comment> getCommentPageInfo(int pageNum, int pageSize);

    PageInfo<Comment> getCommentPageInfoByQuery(String query, int pageNum, int pageSize);

    int deleteCommentById(String id);

    int deleteCommentByIds(List<String> asList);

}
