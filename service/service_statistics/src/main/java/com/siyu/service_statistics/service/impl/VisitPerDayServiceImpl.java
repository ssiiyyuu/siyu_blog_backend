package com.siyu.service_statistics.service.impl;

import com.siyu.service_statistics.entity.VisitPerDay;
import com.siyu.service_statistics.mapper.VisitPerDayMapper;
import com.siyu.service_statistics.service.VisitPerDayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-23 10:03:32
 */
@Service
public class VisitPerDayServiceImpl extends ServiceImpl<VisitPerDayMapper, VisitPerDay> implements VisitPerDayService {

    @Autowired
    VisitPerDayMapper visitPerDayMapper;

    @Override
    public void saveTotal(int pv, int uv) {
        visitPerDayMapper.saveTotal(pv, uv);
    }

    @Override
    public Map<String, Integer> getVisitTotal() {
        Map<String, Integer> res = visitPerDayMapper.selectVisitTotal();
        return res;
    }

}
