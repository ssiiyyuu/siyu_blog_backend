package com.siyu.service_blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siyu.common_static.enums.VisitorLoggerEnum;
import com.siyu.service_base.result.Result;
import com.siyu.service_blog.annotation.VisitLogger;
import com.siyu.service_blog.client.StatisticsClient;

import io.swagger.annotations.Api;

@Api(description="访客地图管理")
@RestController
@RequestMapping("/service_blog/map")
public class MapController {

    @Autowired
    private StatisticsClient statisticsClient;

    @VisitLogger(VisitorLoggerEnum.MAP)
    @GetMapping
    public Result getMap() {
        return statisticsClient.getMap();
    }

}
