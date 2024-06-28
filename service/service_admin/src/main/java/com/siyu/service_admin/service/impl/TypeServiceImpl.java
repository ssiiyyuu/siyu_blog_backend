package com.siyu.service_admin.service.impl;

import com.siyu.service_admin.entity.Type;
import com.siyu.service_admin.entity.dto.TypeDto;
import com.siyu.service_admin.mapper.TypeMapper;
import com.siyu.service_admin.service.TypeService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-19 03:15:42
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public PageInfo<TypeDto> getTypePageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TypeDto> typeList = typeMapper.selectTypeList();
        PageInfo<TypeDto> pageInfo = new PageInfo<>(typeList);
        return pageInfo;
    }

    @Override
    public PageInfo<TypeDto> getTypePageInfoByQuery(String query, int pageNum, int pageSize) {
        List<TypeDto> typeList = null;
        PageHelper.startPage(pageNum, pageSize);
        if(!StringUtils.isEmpty(query)) {
            typeList = typeMapper.selectTypeListByQuery(query);
        } else {
            typeList = typeMapper.selectTypeList();
        }
        PageInfo<TypeDto> pageInfo = new PageInfo<>(typeList);
        return pageInfo;
    }
}
