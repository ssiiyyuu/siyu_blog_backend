package com.siyu.service_blog.service;

import com.siyu.service_blog.entity.Type;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-07 08:29:17
 */
public interface TypeService extends IService<Type> {

    public List<Type> getTypeList();
}
