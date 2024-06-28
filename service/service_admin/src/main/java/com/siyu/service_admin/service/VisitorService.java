package com.siyu.service_admin.service;

import com.siyu.service_admin.entity.Visitor;
import com.siyu.service_admin.entity.dto.LogQueryDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-22 04:34:30
 */
public interface VisitorService extends IService<Visitor> {

    Page<Visitor> getVisitorPage(int pageNum, int pageSize);

    Page<Visitor> getVisitorPageByQuery(LogQueryDto query, int pageNum, int pageSize);

}
