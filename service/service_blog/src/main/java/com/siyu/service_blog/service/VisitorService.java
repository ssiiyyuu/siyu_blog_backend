package com.siyu.service_blog.service;

import com.siyu.service_blog.entity.Visitor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-11 08:27:20
 */
public interface VisitorService extends IService<Visitor> {

    void saveVisitor(String UUID, String IP, String UA);
}
