package com.siyu.service_blog.service.impl;

import com.siyu.common_static.enums.ExceptionEnum;
import com.siyu.common_utils.CastUtil;
import com.siyu.common_utils.JwtUtil;
import com.siyu.service_base.exceptionHandler.GlobalException;
import com.siyu.service_blog.entity.Blog;
import com.siyu.service_blog.entity.Comment;
import com.siyu.service_blog.entity.dto.CommentDto;
import com.siyu.service_blog.entity.vo.CommentVo;
import com.siyu.service_blog.mapper.BlogMapper;
import com.siyu.service_blog.mapper.CommentMapper;
import com.siyu.service_blog.service.CommentService;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-12 05:15:09
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public PageInfo<CommentVo> getCommentVoPageByBlogId(String id, int pageNum, int pageSize) {
        Blog blog = blogMapper.selectOne(
            new QueryWrapper<Blog>().eq("id", id)
                                    .eq("published", 1)
        );
        if(blog == null) {
            throw new GlobalException(ExceptionEnum.NOT_FOUND);
        }
        if(!blog.getCommentable()) {
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<CommentVo> commentVoList = commentMapper.selectCommentListByBlogId(id);
        PageInfo<CommentVo> commentVoPage = new PageInfo<>(commentVoList); 
        return commentVoPage;
    }

    @Override
    public String saveComment(CommentDto comment, String token) {
        if(!comment.valid()) {
            throw new GlobalException(ExceptionEnum.MISSING_PARAM);
        }
        Blog blog = blogMapper.selectOne(
            new QueryWrapper<Blog>().eq("id", comment.getBlogId())
                                    .eq("published", 1)
        );
        if(blog == null) {
            throw new GlobalException(ExceptionEnum.NOT_FOUND);
        }
        if(!blog.getCommentable()) {
            throw new GlobalException(ExceptionEnum.NO_PERMISSION);
        }
        Comment saveComment = new Comment();
        BeanUtils.copyProperties(comment, saveComment);
        if(!StringUtils.isEmpty(token)) {
            Map<String, Object> user = JwtUtil.parseToken(token);
            
            List<String> roles = CastUtil.object2List(user.get("roles"), String.class);
            if(!roles.get(0).equals("admin")) {
                throw new GlobalException(ExceptionEnum.ILLEGAL_TOKEN);
            }
            String name = (String) user.get("name");
            String avatar = (String) user.get("avatar");
            saveComment.setName(name);
            saveComment.setAvatar(avatar);
            saveComment.setIsAdmin(true);
        } else {
            saveComment.setAvatar(getDefaultAvatar(saveComment.getName()));
        }
        commentMapper.insert(saveComment);
        return saveComment.getId();
    }

    public String getDefaultAvatar(String name) {
        int index = Math.abs(name.hashCode() % 9);
        List<String> avatars = Arrays.asList(
            "http://img.siyu.site/avatar/avatar0.jpg",
            "http://img.siyu.site/avatar/avatar1.jpg",
            "http://img.siyu.site/avatar/avatar2.jpg",
            "http://img.siyu.site/avatar/avatar3.jpg",
            "http://img.siyu.site/avatar/avatar4.jpg",
            "http://img.siyu.site/avatar/avatar5.jpg",
            "http://img.siyu.site/avatar/avatar6.jpg",
            "http://img.siyu.site/avatar/avatar7.jpg",
            "http://img.siyu.site/avatar/avatar8.jpg"
        );
        return avatars.get(index);
    }
}
