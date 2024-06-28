package com.siyu.service_statistics.service.impl;

import com.siyu.service_statistics.entity.Blog;
import com.siyu.service_statistics.mapper.BlogMapper;
import com.siyu.service_statistics.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author siyu
 * @since 2023-09-23 11:55:41
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
