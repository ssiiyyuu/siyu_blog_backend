package com.siyu.service_statistics.mapper;

import com.siyu.service_statistics.entity.VisitPerDay;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author siyu
 * @since 2023-09-23 10:03:32
 */
@Mapper
public interface VisitPerDayMapper extends BaseMapper<VisitPerDay> {

    public void saveTotal(int pv, int uv);

    public Map<String, Integer> selectVisitTotal();

}
