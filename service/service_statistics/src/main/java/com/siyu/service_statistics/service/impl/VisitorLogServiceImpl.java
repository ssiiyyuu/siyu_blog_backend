package com.siyu.service_statistics.service.impl;

import com.siyu.service_statistics.entity.VisitorLog;
import com.siyu.service_statistics.entity.Dto.VisitorLogDto;
import com.siyu.service_statistics.mapper.VisitorLogMapper;
import com.siyu.service_statistics.service.VisitorLogService;
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
 * @since 2023-09-23 09:29:05
 */
@Service
public class VisitorLogServiceImpl extends ServiceImpl<VisitorLogMapper, VisitorLog> implements VisitorLogService {

    @Autowired
    VisitorLogMapper visitorLogMapper;
    
    @Override
    public List<VisitorLogDto> getVisitorLogDtoListYesterdat() {
        return visitorLogMapper.selectVisitorLogDtoListYesterday();
    }

}
