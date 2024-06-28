package com.siyu.service_blog.service;

import com.siyu.service_blog.entity.Blog;
import com.siyu.service_blog.entity.vo.BlogDetailVo;
import com.siyu.service_blog.entity.vo.BlogInfoVo;
import com.siyu.service_blog.entity.vo.BlogRepositoryVo;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-07 08:29:41
 */
public interface BlogService extends IService<Blog> {

    public Map<String, Map<String, List<BlogRepositoryVo>>> getRepository();

    public BlogDetailVo getBlogDetailVoById(String id);

    public List<BlogInfoVo> getNewBlogInfoList(int num);

    public PageInfo<BlogInfoVo> getBlogInfoVoList(int pageNum, int pageSize);

    public PageInfo<BlogInfoVo> getBlogInfoVoListByQuery(String query, int pageNum, int pageSize);

    public PageInfo<BlogInfoVo> getBlogInfoVoListByTagTitle(String title, int pageNum, int pageSize);

    public PageInfo<BlogInfoVo> getBlogInfoVoListByTypeTitle(String title, int pageNum, int pageSize);


}
