package com.siyu.service_admin.mapper;

import com.siyu.service_admin.entity.Type;
import com.siyu.service_admin.entity.dto.TypeDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author siyu
 * @since 2023-09-19 03:15:42
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    List<TypeDto> selectTypeList();

    List<TypeDto> selectTypeListByQuery(@Param("query") String query);

}
