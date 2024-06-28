package com.siyu.service_admin.service;

import com.siyu.service_admin.entity.Type;
import com.siyu.service_admin.entity.dto.TypeDto;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-19 03:15:42
 */
public interface TypeService extends IService<Type> {

    public PageInfo<TypeDto> getTypePageInfo(int pageNum, int pageSize);

    public PageInfo<TypeDto> getTypePageInfoByQuery(String query, int pageNum, int pageSize);
}
