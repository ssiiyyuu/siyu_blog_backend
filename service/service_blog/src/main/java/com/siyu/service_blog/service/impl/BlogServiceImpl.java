package com.siyu.service_blog.service.impl;

import com.siyu.common_static.constant.RedisConstant;
import com.siyu.common_static.enums.ExceptionEnum;
import com.siyu.common_utils.MarkdownUtil;
import com.siyu.common_utils.CastUtil;
import com.siyu.common_utils.RedisUtil.RedisUtil;
import com.siyu.service_base.exceptionHandler.GlobalException;
import com.siyu.service_blog.entity.Blog;
import com.siyu.service_blog.entity.dto.BlogDto;
import com.siyu.service_blog.entity.vo.BlogDetailVo;
import com.siyu.service_blog.entity.vo.BlogInfoVo;
import com.siyu.service_blog.entity.vo.BlogRepositoryVo;
import com.siyu.service_blog.mapper.BlogMapper;
import com.siyu.service_blog.mapper.BlogTagRelationMapper;
import com.siyu.service_blog.mapper.wrapper.CustomWrapper;
import com.siyu.service_blog.service.BlogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-07 08:29:41
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired 
    private BlogMapper blogMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public PageInfo<BlogInfoVo> getBlogInfoVoListByTagTitle(String title, int pageNum, int pageSize) {
        List<String> blogIds = blogTagRelationMapper.selectBlogIdsByTagTitle(title);
        if(blogIds.size() == 0) {
            return new PageInfo<>();
        }
        PageHelper.startPage(pageNum, pageSize);
        List<BlogDto> blogDtoList = blogMapper.selectBlogDtoList(
            CustomWrapper.builder()
                         .cols(Arrays.asList("blog.id"))
                         .mode("in")
                         .props(blogIds)
                         .build()
        );
        PageInfo<BlogDto> blogPageInfo = new PageInfo<>(blogDtoList);
        return CastUtil.dto2vo(blogPageInfo, BlogInfoVo.class);
    }

    @Override
    public PageInfo<BlogInfoVo> getBlogInfoVoListByTypeTitle(String title, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BlogDto> blogDtoList = blogMapper.selectBlogDtoList(
            CustomWrapper.builder()
                         .cols(Arrays.asList("type.title"))
                         .mode("=")
                         .prop(title)
                         .build()
        );
        PageInfo<BlogDto> blogPageInfo = new PageInfo<>(blogDtoList);
        return CastUtil.dto2vo(blogPageInfo, BlogInfoVo.class);
    }

    @Override
    public PageInfo<BlogInfoVo> getBlogInfoVoList(int pageNum, int pageSize) {
        PageInfo<BlogInfoVo> pageInfo = redisUtil.getPageInfoFromHash(RedisConstant.HOME_PAGE_HASH_KEY, pageNum);
        if(pageInfo != null) {
            return pageInfo;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<BlogDto> blogDtoList = blogMapper.selectBlogDtoList(null);
        if(blogDtoList.size() == 0) { //没查到内容
            return PageInfo.emptyPageInfo();
        }
        PageInfo<BlogDto> blogPageInfo = new PageInfo<>(blogDtoList);
        PageInfo<BlogInfoVo> blogVoPageInfo = CastUtil.dto2vo(blogPageInfo, BlogInfoVo.class);
        redisUtil.saveToHashByHashKey(RedisConstant.HOME_PAGE_HASH_KEY, pageNum, blogVoPageInfo);
        return blogVoPageInfo;
    }

    @Override
    public PageInfo<BlogInfoVo> getBlogInfoVoListByQuery(String query, int pageNum, int pageSize) {
        CustomWrapper wrapper = StringUtils.isEmpty(query) ? 
            null : CustomWrapper.builder()
                                .cols(Arrays.asList("blog.title", "blog.author", "blog.description", "blog.content"))
                                .mode("like")
                                .prop(query.trim())
                                .build();
        PageHelper.startPage(pageNum, pageSize);
        List<BlogDto> blogDtoList = blogMapper.selectBlogDtoList(wrapper);
        if(blogDtoList.size() == 0) { //没查到内容
            return PageInfo.emptyPageInfo();
        }
        PageInfo<BlogDto> blogPageInfo = new PageInfo<>(blogDtoList);
        PageInfo<BlogInfoVo> blogVoPageInfo = CastUtil.dto2vo(blogPageInfo, BlogInfoVo.class);
        return blogVoPageInfo;
    }

    @Override
    public List<BlogInfoVo> getNewBlogInfoList(int num) {
        List<BlogInfoVo> blogInfoVoList = redisUtil.getFromList(RedisConstant.NEW_BLOG_LIST_KEY, BlogInfoVo.class);
        if(blogInfoVoList != null) {
            return blogInfoVoList;
        }
        List<Blog> blogList = blogMapper.selectList(
            new QueryWrapper<Blog>().eq("published", true)
                                    .orderByDesc("gmt_create")
                                    .last("limit " + num)
        );
        blogInfoVoList = blogList.stream().map(
            blog -> {
                BlogInfoVo blogInfoVo = new BlogInfoVo();
                BeanUtils.copyProperties(blog, blogInfoVo);
                return blogInfoVo;
            }
        ).collect(Collectors.toList());
        redisUtil.saveToList(RedisConstant.NEW_BLOG_LIST_KEY, blogInfoVoList);
        return blogInfoVoList;
    }

    @Override
    public BlogDetailVo getBlogDetailVoById(String id) {
        BlogDto blogDto = blogMapper.selectBlogDtoById(id);
        if(blogDto == null) {
            throw new GlobalException(ExceptionEnum.NOT_FOUND);
        }
        BlogDetailVo blogDetailVo = new BlogDetailVo();
        BeanUtils.copyProperties(blogDto, blogDetailVo);
        blogDetailVo.setContent(
            MarkdownUtil.md2html(blogDetailVo.getContent())
        );
        return blogDetailVo;
    }
    // {
    //     "2022": {
    //         "2022-11-12": [
    //             blog1, blog2, blog3...
    //         ],            
    //         "2022-11-13": [
    //             blog1, blog2, blog3...
    //         ]
    //     },
    //     "2023": {
    //         "2023-11-12": [
    //             blog1, blog2, blog3...
    //         ],            
    //         "2023-11-13": [
    //             blog1, blog2, blog3...
    //         ]
    //     }
    // }
    @Override
    public Map<String, Map<String, List<BlogRepositoryVo>>> getRepository() {
        Map<String, Map<String, List<BlogRepositoryVo>>> map = new LinkedHashMap<>();
        List<Blog> blogs = blogMapper.selectList(
            new QueryWrapper<Blog>().eq("published", true).orderByAsc("gmt_create")
        );
        blogs.forEach(
            blog -> {
                BlogRepositoryVo vo = new BlogRepositoryVo();
                BeanUtils.copyProperties(blog, vo);
                LocalDateTime time = blog.getGmtCreate();
                String year = time.format(DateTimeFormatter.ofPattern("yyyy"));
                String day = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if(map.containsKey(year)) {
                    Map<String, List<BlogRepositoryVo>> subMap = map.get(year);
                    if(subMap.containsKey(day)) {
                        subMap.get(day).add(vo);
                    } else {
                        subMap.put(day, new ArrayList<>() {{add(vo);}});
                    }
                } else {
                    Map<String, List<BlogRepositoryVo>> subMap = new LinkedHashMap<>();
                    subMap.put(day, new ArrayList<>() {{add(vo);}});
                    map.put(year, subMap);
                }
            }
        );
        return map;
    }

}
