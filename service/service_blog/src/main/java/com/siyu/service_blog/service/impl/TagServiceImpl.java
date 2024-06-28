package com.siyu.service_blog.service.impl;

import com.siyu.common_static.constant.RedisConstant;
import com.siyu.common_utils.RedisUtil.RedisUtil;
import com.siyu.service_blog.entity.Tag;
import com.siyu.service_blog.mapper.TagMapper;
import com.siyu.service_blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-07 08:29:30
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Tag> getTagList() {
        List<Tag> tagList = redisUtil.getFromList(RedisConstant.TAG_LIST_KEY, Tag.class);
        if(tagList != null) {
            return tagList;
        }
        tagList = tagMapper.selectList(null);
        redisUtil.saveToList(RedisConstant.TAG_LIST_KEY, tagList);
        return tagList;
    }

}
