package com.siyu.service_blog.service;

import com.siyu.service_blog.entity.Tag;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-07 08:29:30
 */
public interface TagService extends IService<Tag> {

    public List<Tag> getTagList();
    
}
