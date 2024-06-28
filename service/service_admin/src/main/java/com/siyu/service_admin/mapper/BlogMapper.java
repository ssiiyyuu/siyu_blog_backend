package com.siyu.service_admin.mapper;

import com.siyu.service_admin.entity.Blog;
import com.siyu.service_admin.entity.dto.BlogExcel;
import com.siyu.service_admin.entity.vo.BlogInfoVo;
import com.siyu.service_admin.entity.vo.BlogPublishVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author siyu
 * @since 2023-09-20 02:36:29
 */
public interface BlogMapper extends BaseMapper<Blog> {

    String selectBlogContentById(@Param("id") String id);

    BlogInfoVo selectBlogInfoById(@Param("id") String id);

    BlogPublishVo selectBlogPublishVoById(@Param("id") String id);

    List<BlogExcel> selectBlogExcel();

}
