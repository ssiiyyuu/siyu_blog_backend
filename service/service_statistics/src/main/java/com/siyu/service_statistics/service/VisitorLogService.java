package com.siyu.service_statistics.service;

import com.siyu.service_statistics.entity.VisitorLog;
import com.siyu.service_statistics.entity.Dto.VisitorLogDto;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-23 09:29:05
 */
public interface VisitorLogService extends IService<VisitorLog> {
    public List<VisitorLogDto> getVisitorLogDtoListYesterdat();
}
