package com.siyu.service_blog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.siyu.service_base.result.Result;

@FeignClient("service-statistics")
@Component
public interface StatisticsClient {
    
    @GetMapping("/service_statistics/map")
    public Result getMap();
    
}
