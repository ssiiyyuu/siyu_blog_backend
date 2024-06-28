package com.siyu.service_blog.mapper;

import com.siyu.service_blog.entity.BlogTagRelation;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author siyu
 * @since 2023-09-11 10:31:08
 */
public interface BlogTagRelationMapper extends BaseMapper<BlogTagRelation> {

    List<String> selectBlogIdsByTagTitle(String title);

    List<String> selectBlogIdsByTagId(String id);
        
    List<String> selectTagIdsByBlogId(String id);
}
