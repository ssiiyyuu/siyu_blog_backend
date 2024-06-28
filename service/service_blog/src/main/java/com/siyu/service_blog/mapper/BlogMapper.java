package com.siyu.service_blog.mapper;

import com.siyu.service_blog.entity.Blog;
import com.siyu.service_blog.entity.dto.BlogDto;
import com.siyu.service_blog.mapper.wrapper.CustomWrapper;

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
 * @since 2023-09-07 08:29:41
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    public BlogDto selectBlogDtoById(@Param("id") String id);
    public List<BlogDto> selectBlogDtoList(@Param("wrapper") CustomWrapper wrapper);
}
