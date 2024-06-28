package com.siyu.service_statistics.service.impl;

import com.siyu.common_static.constant.RedisConstant;
import com.siyu.common_utils.RedisUtil.RedisUtil;
import com.siyu.service_statistics.entity.VisitorAddress;
import com.siyu.service_statistics.mapper.VisitorAddressMapper;
import com.siyu.service_statistics.service.VisitorAddressService;
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
 * @since 2023-09-23 11:16:44
 */
@Service
public class VisitorAddressServiceImpl extends ServiceImpl<VisitorAddressMapper, VisitorAddress> implements VisitorAddressService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private VisitorAddressMapper visitorAddressMapper;

    @Override
    public List<VisitorAddress> getUvData() {
        List<VisitorAddress> uv = redisUtil.getFromList(RedisConstant.VISITOR_LIST_KEY, VisitorAddress.class);
        if(uv != null) {
            return uv;
        }
        uv =  visitorAddressMapper.selectList(null);
        redisUtil.saveToList(RedisConstant.VISITOR_LIST_KEY, uv);
        return uv;
    }
}
