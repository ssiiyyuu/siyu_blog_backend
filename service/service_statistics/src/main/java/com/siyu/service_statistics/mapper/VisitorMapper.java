package com.siyu.service_statistics.mapper;

import com.siyu.service_statistics.entity.Visitor;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author siyu
 * @since 2023-09-23 10:11:39
 */
@Mapper
public interface VisitorMapper extends BaseMapper<Visitor> {

    public List<String> selectVisitorAddressYesterday();
}
