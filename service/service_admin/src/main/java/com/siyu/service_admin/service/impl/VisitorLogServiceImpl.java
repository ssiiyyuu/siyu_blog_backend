package com.siyu.service_admin.service.impl;

import com.siyu.service_admin.entity.VisitorLog;
import com.siyu.service_admin.entity.dto.LogQueryDto;
import com.siyu.service_admin.mapper.VisitorLogMapper;
import com.siyu.service_admin.service.VisitorLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-22 04:46:24
 */
@Service
public class VisitorLogServiceImpl extends ServiceImpl<VisitorLogMapper, VisitorLog> implements VisitorLogService {
    @Autowired
    private VisitorLogMapper visitorlogMapper;

    @Override
    public Page<VisitorLog> getVisitorLogPage(int pageNum, int pageSize) {
        Page<VisitorLog> visitorlogPage = new Page<>(pageNum, pageSize);
        visitorlogMapper.selectPage(visitorlogPage, new QueryWrapper<VisitorLog>().orderByDesc("gmt_create"));
        return visitorlogPage;
    }

    @Override
    public Page<VisitorLog> getVisitorLogPageByQuery(LogQueryDto query, int pageNum, int pageSize) {
        Page<VisitorLog> visitorlogPage = new Page<>(pageNum, pageSize);
        QueryWrapper<VisitorLog> wrapper = new QueryWrapper<>();

        String uuid = query.getUuid();
        LocalDateTime begin = query.getBegin();
        LocalDateTime end = query.getEnd();

        if(!StringUtils.isEmpty(uuid)) {
            wrapper.eq("uuid", uuid);
        }
        if(begin != null && end != null) {
            wrapper.between("gmt_create", begin, end);
        }
        wrapper.orderByDesc("gmt_create");
        visitorlogMapper.selectPage(visitorlogPage, wrapper);
        return visitorlogPage;
    }
}
