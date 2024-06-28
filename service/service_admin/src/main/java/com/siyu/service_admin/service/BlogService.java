package com.siyu.service_admin.service;

import com.siyu.service_admin.entity.Blog;
import com.siyu.service_admin.entity.dto.BlogDto;
import com.siyu.service_admin.entity.dto.BlogExcel;
import com.siyu.service_admin.entity.dto.BlogQueryDto;
import com.siyu.service_admin.entity.vo.BlogInfoVo;
import com.siyu.service_admin.entity.vo.BlogPublishVo;
import com.siyu.service_admin.entity.vo.BlogVo;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-20 02:36:29
 */
public interface BlogService extends IService<Blog> {
    
    String saveOrUpdateBlog(BlogDto blogInfoDto, String token);

    IPage<BlogVo> getBlogPage(int pageNum, int pageSize);

    IPage<BlogVo> getBlogPageByQuery(BlogQueryDto query, int pageNum, int pageSize);

    BlogInfoVo getBlogInfoVoById(String id);

    String getBlogContentVoById(String id);

    BlogPublishVo getBlogPublishVoById(String id);

    Boolean deleteBlogById(String id);

    Boolean deleteBlogByIds(List<String> ids);

    List<BlogExcel> getBlogExcel();

}
