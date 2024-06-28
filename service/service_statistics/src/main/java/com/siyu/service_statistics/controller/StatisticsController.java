package com.siyu.service_statistics.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.siyu.service_base.result.Result;
import com.siyu.service_statistics.entity.VisitPerDay;
import com.siyu.service_statistics.service.VisitPerDayService;
import com.siyu.service_statistics.service.VisitorAddressService;

@RestController
@RequestMapping("/service_statistics")
public class StatisticsController {

    @Autowired
    private VisitPerDayService visitPerDayService;
    
    @Autowired
    private VisitorAddressService visitorAddressService;

    @GetMapping("/map")
    public Result getMap() {
        return Result.ok().data("records", visitorAddressService.getUvData());
    }

    @GetMapping("/records")
    public Map<String, Object> getDashboardData() {
        //查询14天pv、uv记录
        List<VisitPerDay> visitPerDays = visitPerDayService.list(
            new QueryWrapper<VisitPerDay>()
                .orderByDesc("date")
                .last("limit 14")
        );
        List<String> dateList = new ArrayList<>();
        List<Integer> pvList = new ArrayList<>();
        List<Integer> uvList = new ArrayList<>();
        visitPerDays.forEach(
            day -> {
                dateList.add(day.getDate().format(DateTimeFormatter.ofPattern("MM-dd")).toString());
                pvList.add(day.getPv());
                uvList.add(day.getUv());
            }
        );

        Map<String, Object> res = new HashMap<>();
        Map<String, Integer> visitData = visitPerDayService.getVisitTotal();
        Map<String, Object> lineData = new HashMap<>();
        lineData.put("pv", pvList);
        lineData.put("uv", uvList);
        lineData.put("date", dateList);
        res.putAll(visitData);
        res.put("lineData", lineData);
        return res;
    }
}
