package com.siyu.service_blog.mapper;

import com.siyu.service_blog.entity.Tag;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author siyu
 * @since 2023-09-07 08:29:30
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    public List<String> getBlogTags(String id);
}
