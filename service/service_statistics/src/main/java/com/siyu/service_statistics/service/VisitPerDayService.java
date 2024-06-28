package com.siyu.service_statistics.service;

import com.siyu.service_statistics.entity.VisitPerDay;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-23 10:03:32
 */
public interface VisitPerDayService extends IService<VisitPerDay> {

    void saveTotal(int pv, int uv);

    Map<String, Integer> getVisitTotal();

}
