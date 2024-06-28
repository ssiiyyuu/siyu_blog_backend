package com.siyu.service_blog.mapper;

import com.siyu.service_blog.entity.Comment;
import com.siyu.service_blog.entity.vo.CommentVo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author siyu
 * @since 2023-09-12 05:15:09
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    public List<CommentVo> selectCommentListByBlogId(@Param("id") String id);
}
