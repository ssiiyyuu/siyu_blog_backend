package com.siyu.service_admin.service.impl;

import com.siyu.service_admin.entity.Visitor;
import com.siyu.service_admin.entity.dto.LogQueryDto;
import com.siyu.service_admin.mapper.VisitorMapper;
import com.siyu.service_admin.service.VisitorService;
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
 * @since 2023-09-22 04:34:30
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public Page<Visitor> getVisitorPage(int pageNum, int pageSize) {
        Page<Visitor> visitorPage = new Page<>(pageNum, pageSize);
        visitorMapper.selectPage(visitorPage, new QueryWrapper<Visitor>().orderByDesc("gmt_create"));
        return visitorPage;
    }

    @Override
    public Page<Visitor> getVisitorPageByQuery(LogQueryDto query, int pageNum, int pageSize) {
        Page<Visitor> visitorPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();

        LocalDateTime begin = query.getBegin();
        LocalDateTime end = query.getEnd();

        if(begin != null && end != null) {
            wrapper.between("gmt_create", begin, end);
        }
        wrapper.orderByDesc("gmt_create");
        visitorMapper.selectPage(visitorPage, wrapper);
        return visitorPage;
    }

}
