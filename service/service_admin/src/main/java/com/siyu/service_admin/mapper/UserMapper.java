package com.siyu.service_admin.mapper;

import com.siyu.service_admin.entity.User;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author siyu
 * @since 2023-09-17 04:26:42
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
