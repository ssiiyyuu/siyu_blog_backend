package com.siyu.service_blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siyu.common_static.enums.VisitorLoggerEnum;
import com.siyu.service_base.result.Result;
import com.siyu.service_blog.annotation.VisitLogger;
import com.siyu.service_blog.entity.Tag;
import com.siyu.service_blog.entity.Type;
import com.siyu.service_blog.entity.vo.BlogInfoVo;
import com.siyu.service_blog.service.BlogService;
import com.siyu.service_blog.service.TagService;
import com.siyu.service_blog.service.TypeService;

@RestController
@RequestMapping("/service_blog")
public class IndexController {
    @Autowired
    private BlogService blogService;
    
    @Autowired
    private TagService tagService;
    
    @Autowired
    private TypeService typeService;

    private static Integer NEW_BLOG = 4;

    @GetMapping("/index")
    @VisitLogger(VisitorLoggerEnum.HOME)
    public Result getIndex() {
        List<BlogInfoVo> blogInfoVos = blogService.getNewBlogInfoList(NEW_BLOG);
        List<Tag> tags = tagService.getTagList();
        List<Type> types = typeService.getTypeList();
        return Result.ok().data("blogs", blogInfoVos)
                          .data("tags", tags)
                          .data("types", types);
    }
}
