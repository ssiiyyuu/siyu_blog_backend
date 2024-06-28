package com.siyu.service_statistics.service.impl;

import com.siyu.service_statistics.entity.Visitor;
import com.siyu.service_statistics.mapper.VisitorMapper;
import com.siyu.service_statistics.service.VisitorService;
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
 * @since 2023-09-23 10:11:39
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Autowired
    VisitorMapper visitorMapper;

    @Override
    public List<String> getVisitorAddressYesterday() {
       return visitorMapper.selectVisitorAddressYesterday();
    }

}
