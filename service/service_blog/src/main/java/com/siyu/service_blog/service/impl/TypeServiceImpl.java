package com.siyu.service_blog.service.impl;

import com.siyu.common_static.constant.RedisConstant;
import com.siyu.common_utils.RedisUtil.RedisUtil;
import com.siyu.service_blog.entity.Type;
import com.siyu.service_blog.mapper.TypeMapper;
import com.siyu.service_blog.service.TypeService;
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
 * @since 2023-09-07 08:29:17
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<Type> getTypeList() {
        List<Type> TypeList = redisUtil.getFromList(RedisConstant.TYPE_LIST_KEY, Type.class);
        if(TypeList != null) {
            return TypeList;
        }
        TypeList = typeMapper.selectList(null);
        redisUtil.saveToList(RedisConstant.TYPE_LIST_KEY, TypeList);
        return TypeList;
    }

}
