package com.siyu.service_statistics.service;

import com.siyu.service_statistics.entity.VisitorAddress;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-23 11:16:44
 */
public interface VisitorAddressService extends IService<VisitorAddress> {
    public List<VisitorAddress> getUvData();
}
