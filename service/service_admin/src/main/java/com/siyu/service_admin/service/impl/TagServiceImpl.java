package com.siyu.service_admin.service.impl;

import com.siyu.service_admin.entity.BlogTagRelation;
import com.siyu.service_admin.entity.Tag;
import com.siyu.service_admin.entity.dto.TagDto;
import com.siyu.service_admin.mapper.BlogTagRelationMapper;
import com.siyu.service_admin.mapper.TagMapper;
import com.siyu.service_admin.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-19 02:29:03
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public PageInfo<TagDto> getTagPageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TagDto> tagList = tagMapper.selectTagList();
        PageInfo<TagDto> pageInfo = new PageInfo<>(tagList);
        return pageInfo;
    }

    @Override
    public PageInfo<TagDto> getTagPageInfoByQuery(String query, int pageNum, int pageSize) {
        List<TagDto> tagList = null;
        PageHelper.startPage(pageNum, pageSize);
        if(!StringUtils.isEmpty(query)) {
            tagList = tagMapper.selectTagListByQuery(query);
        } else {
            tagList = tagMapper.selectTagList();
        }
        PageInfo<TagDto> pageInfo = new PageInfo<>(tagList);
        return pageInfo;
    }

    @Override
    public Boolean deleteTagById(String id) {
        try {
            blogTagRelationMapper.delete(new QueryWrapper<BlogTagRelation>().eq("tag_id", id));

            return tagMapper.deleteById(id) > 0;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteTagByIds(List<String> ids) {
        try {
            QueryWrapper<BlogTagRelation> wrapper = new QueryWrapper<>();
            wrapper.in("tag_id", ids);
            blogTagRelationMapper.delete(wrapper);
            return tagMapper.deleteBatchIds(ids) > 0;
        } catch(Exception e) {
            return false;
        }
    }
}
