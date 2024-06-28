package com.siyu.service_admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siyu.service_admin.client.StatisticsClient;
import com.siyu.service_admin.service.BlogService;
import com.siyu.service_admin.service.CommentService;
import com.siyu.service_base.result.Result;

@RestController
@RequestMapping("/service_admin/dashboard")
public class DashboardController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private StatisticsClient statisticsClient;

    @GetMapping
    public Result getDashboardData() {
        long blogCount = blogService.count();
        long commentCount = commentService.count();
        Map<String, Object> statisticsMap = statisticsClient.getDashboardData();
        return Result.ok().data(statisticsMap)
                          .data("blogCount", blogCount)
                          .data("commentCount", commentCount);
    }
}
