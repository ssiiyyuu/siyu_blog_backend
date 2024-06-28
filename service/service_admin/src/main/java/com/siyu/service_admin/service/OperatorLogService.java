package com.siyu.service_admin.service;

import com.siyu.service_admin.entity.OperatorLog;
import com.siyu.service_admin.entity.dto.LogQueryDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-22 05:05:30
 */
public interface OperatorLogService extends IService<OperatorLog> {

    Page<OperatorLog> getOperatorLogPage(int pageNum, int pageSize);

    Page<OperatorLog> getOperatorLogPageByQuery(LogQueryDto query, int pageNum, int pageSize);

}
