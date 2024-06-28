package com.siyu.service_admin.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("service-statistics")
@Component
public interface StatisticsClient {
    
    @GetMapping("/service_statistics/records")
    public Map<String, Object> getDashboardData();
    
}
