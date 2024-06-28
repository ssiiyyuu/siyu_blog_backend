package com.siyu.service_admin.service.impl;

import com.siyu.service_admin.entity.Comment;
import com.siyu.service_admin.mapper.CommentMapper;
import com.siyu.service_admin.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-24 12:40:08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public PageInfo<Comment> getCommentPageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> commentList = commentMapper.selectList(new QueryWrapper<Comment>().orderByDesc("gmt_create"));
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        return pageInfo;
    }

    @Override
    public PageInfo<Comment> getCommentPageInfoByQuery(String query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(query)) {
            wrapper.like("content", query);
        }
        wrapper.orderByDesc("gmt_create");
        List<Comment> commentList = commentMapper.selectList(wrapper);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        return pageInfo;
    }

    @Override
    public int deleteCommentById(String id) {
        int count = 0;
        //先删子评论
        count += commentMapper.delete(new QueryWrapper<Comment>().eq("parent_id", id));
        count += commentMapper.deleteById(id);
        return count;
    }

    @Override
    public int deleteCommentByIds(List<String> ids) {
        int count = 0;
        count += commentMapper.delete(new QueryWrapper<Comment>().in("parent_id", ids));
        count += commentMapper.deleteBatchIds(ids);
        return count;
    }

}
