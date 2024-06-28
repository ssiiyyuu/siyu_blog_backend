package com.siyu.service_admin.service.impl;

import com.siyu.service_admin.entity.Blog;
import com.siyu.service_admin.entity.BlogTagRelation;
import com.siyu.service_admin.entity.dto.BlogDto;
import com.siyu.service_admin.entity.dto.BlogExcel;
import com.siyu.service_admin.entity.dto.BlogQueryDto;
import com.siyu.service_admin.entity.vo.BlogInfoVo;
import com.siyu.service_admin.entity.vo.BlogPublishVo;
import com.siyu.service_admin.entity.vo.BlogVo;
import com.siyu.service_admin.mapper.BlogMapper;
import com.siyu.service_admin.mapper.BlogTagRelationMapper;
import com.siyu.service_admin.service.BlogService;
import com.siyu.service_admin.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-20 02:36:29
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public String saveOrUpdateBlog(BlogDto blogDto, String token) {
        String blogDtoId = blogDto.getId();
        List<String> blogIds = blogDto.getTagIds();

        Blog blog = new Blog();
        BeanUtils.copyProperties(blogDto, blog);
        if(!StringUtils.isEmpty(token)) { //有token 解析并设置author
            blog.setAuthor(
                (String) userService.info(token).get("name")
            );
        }
        if(StringUtils.isEmpty(blogDtoId)) { //没有id insert
            blogMapper.insert(blog);
        } else { //有id update
            blogMapper.updateById(blog);
        }
        //维护博客-标签关系表
        if(blogIds != null && blogIds.size() != 0) {
            if(!StringUtils.isEmpty(blogDtoId)) { //有id且有blog 先删除
                blogTagRelationMapper.delete(new QueryWrapper<BlogTagRelation>().eq("blog_id", blog.getId()));
            }
            blogIds.forEach(
                blogId -> blogTagRelationMapper.insert(new BlogTagRelation(blog.getId(), blogId))
            );
        }
        return blog.getId();
    }

    @Override
    public IPage<BlogVo> getBlogPage(int pageNum, int pageSize) {
        Page<Blog> blogPage = new Page<>(pageNum, pageSize);
        Page<Blog> page = blogMapper.selectPage(
            blogPage, new QueryWrapper<Blog>().orderByDesc(Arrays.asList("sort", "gmt_create"))
        );
        IPage<BlogVo> blogVoPage = page.convert(
            blog -> {
                BlogVo blogVo = new BlogVo();
                BeanUtils.copyProperties(blog, blogVo);
                return blogVo;
            }
        );
        return blogVoPage;
    }

    @Override
    public IPage<BlogVo> getBlogPageByQuery(BlogQueryDto query, int pageNum, int pageSize) {
        Page<Blog> blogPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();

        String title = query.getTitle();
        String author = query.getAuthor();
        Boolean published = query.getPublished();
        LocalDateTime begin = query.getBegin();
        LocalDateTime end = query.getEnd();

        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if(!StringUtils.isEmpty(author)) {
            wrapper.eq("author", author);
        }
        if(published != null) {
            wrapper.eq("published", published);
        }
        if(begin != null && end != null) {
            wrapper.between("gmt_create", begin, end);
        }
        wrapper.orderByDesc(Arrays.asList("sort", "gmt_create"));

        Page<Blog> page = blogMapper.selectPage(blogPage, wrapper);
        
        IPage<BlogVo> blogVoPage = page.convert(
            blog -> {
                BlogVo blogVo = new BlogVo();
                BeanUtils.copyProperties(blog, blogVo);
                return blogVo;
            }
        );
        return blogVoPage;
    }

    @Override
    public BlogInfoVo getBlogInfoVoById(String id) {
        return blogMapper.selectBlogInfoById(id);
    }

    @Override
    public String getBlogContentVoById(String id) {
        return blogMapper.selectBlogContentById(id);
    }

    @Override
    public BlogPublishVo getBlogPublishVoById(String id) {
        return blogMapper.selectBlogPublishVoById(id);
    }

    @Override
    public Boolean deleteBlogById(String id) {
        try {
            blogTagRelationMapper.delete(new QueryWrapper<BlogTagRelation>().eq("blog_id", id));
            return blogMapper.deleteById(id) > 0;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteBlogByIds(List<String> ids) {
        try {
            QueryWrapper<BlogTagRelation> wrapper = new QueryWrapper<>();
            wrapper.in("blog_id", ids);
            blogTagRelationMapper.delete(wrapper);
            return blogMapper.deleteBatchIds(ids) > 0;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public List<BlogExcel> getBlogExcel() {
        return blogMapper.selectBlogExcel();
    }

}
