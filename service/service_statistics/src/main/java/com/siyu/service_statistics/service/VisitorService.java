package com.siyu.service_statistics.service;

import com.siyu.service_statistics.entity.Visitor;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author siyu
 * @since 2023-09-23 10:11:39
 */
public interface VisitorService extends IService<Visitor> {

    public List<String> getVisitorAddressYesterday();
}
