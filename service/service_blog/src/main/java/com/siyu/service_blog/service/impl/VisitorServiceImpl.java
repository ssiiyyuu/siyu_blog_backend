package com.siyu.service_blog.service.impl;

import com.siyu.service_blog.entity.Visitor;
import com.siyu.service_blog.mapper.VisitorMapper;
import com.siyu.service_blog.service.VisitorService;
import com.siyu.service_blog.util.IpUtil;
import com.siyu.service_blog.util.UserAgentUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-11 08:27:20
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    @Autowired
    private UserAgentUtil userAgentUtil;

    @Override
    public void saveVisitor(String UUID, String IP, String UA) {
        String ipAddress = IpUtil.getIpAddress(IP);
        Map<String, String> map = userAgentUtil.parseUserAgent(UA);
        String os = map.get("os");
        String browser = map.get("browser");
        String device = map.get("device");
        visitorMapper.insert(
            Visitor.builder()
                    .uuid(UUID)
                    .ip(IP)
                    .ipAddress(ipAddress)
                    .os(os)
                    .device(device)
                    .browser(browser)
                    .view(0)
                    .build()
        );
    }

}
