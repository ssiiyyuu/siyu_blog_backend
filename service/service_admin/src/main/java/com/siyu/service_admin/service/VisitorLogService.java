package com.siyu.service_admin.service;

import com.siyu.service_admin.entity.VisitorLog;
import com.siyu.service_admin.entity.dto.LogQueryDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-22 04:46:24
 */
public interface VisitorLogService extends IService<VisitorLog> {

    Page<VisitorLog> getVisitorLogPageByQuery(LogQueryDto query, int pageNum, int pageSize);

    Page<VisitorLog> getVisitorLogPage(int pageNum, int pageSize);

}
