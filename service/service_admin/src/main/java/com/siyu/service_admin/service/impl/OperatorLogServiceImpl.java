package com.siyu.service_admin.service.impl;

import com.siyu.service_admin.entity.OperatorLog;
import com.siyu.service_admin.entity.dto.LogQueryDto;
import com.siyu.service_admin.mapper.OperatorLogMapper;
import com.siyu.service_admin.service.OperatorLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class OperatorLogServiceImpl extends ServiceImpl<OperatorLogMapper, OperatorLog> implements OperatorLogService {
    @Autowired
    private OperatorLogMapper operatorlogMapper;

    @Override
    public Page<OperatorLog> getOperatorLogPage(int pageNum, int pageSize) {
        Page<OperatorLog> operatorlogPage = new Page<>(pageNum, pageSize);
        operatorlogMapper.selectPage(operatorlogPage, new QueryWrapper<OperatorLog>().orderByDesc("gmt_create"));
        return operatorlogPage;
    }

    @Override
    public Page<OperatorLog> getOperatorLogPageByQuery(LogQueryDto query, int pageNum, int pageSize) {
        Page<OperatorLog> operatorlogPage = new Page<>(pageNum, pageSize);
        QueryWrapper<OperatorLog> wrapper = new QueryWrapper<>();

        LocalDateTime begin = query.getBegin();
        LocalDateTime end = query.getEnd();

        if(begin != null && end != null) {
            wrapper.between("gmt_create", begin, end);
        }
        wrapper.orderByDesc("gmt_create");
        operatorlogMapper.selectPage(operatorlogPage, wrapper);
        return operatorlogPage;
    }
}
