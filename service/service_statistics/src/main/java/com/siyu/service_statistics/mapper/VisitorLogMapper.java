package com.siyu.service_statistics.mapper;

import com.siyu.service_statistics.entity.VisitorLog;
import com.siyu.service_statistics.entity.Dto.VisitorLogDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author siyu
 * @since 2023-09-23 09:29:05
 */
@Mapper
public interface VisitorLogMapper extends BaseMapper<VisitorLog> {
    public List<VisitorLogDto> selectVisitorLogDtoListYesterday();
}
