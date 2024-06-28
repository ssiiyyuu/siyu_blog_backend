package com.siyu.service_statistics.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.siyu.common_utils.RedisUtil.RedisUtil;
import com.siyu.service_statistics.entity.VisitPerDay;
import com.siyu.service_statistics.entity.Visitor;
import com.siyu.service_statistics.entity.VisitorAddress;
import com.siyu.service_statistics.entity.Dto.VisitorLogDto;
import com.siyu.service_statistics.service.VisitPerDayService;
import com.siyu.service_statistics.service.VisitorAddressService;
import com.siyu.service_statistics.service.VisitorLogService;
import com.siyu.service_statistics.service.VisitorService;

@Configuration
@EnableScheduling
public class VisitorScheduleTask {
    
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private VisitorLogService visitorLogService;

    @Autowired
    private VisitPerDayService visitPerDayService;

    @Autowired
    private VisitorAddressService visitorAddressService;

    // 每日执行一次
    @Scheduled(cron = "0 0 0 * * ?")
    public void redisToDatabaseTask() {
        redisUtil.clearIdentifierCache();
        //获取昨日访客日志 根据日志记录PV、UV并保存至数据库
        List<VisitorLogDto> visitorLogList = visitorLogService.getVisitorLogDtoListYesterdat();
        Map<String, Integer> pvMap = new HashMap<>(1024);
        Map<String, LocalDateTime> visitTimeMap = new HashMap<>(1024);
        visitorLogList.forEach(
            visitLog -> {
                String uuid = visitLog.getUuid();
                LocalDateTime time = visitLog.getGmtCreate();
                pvMap.merge(uuid, 1, (pre, next) -> pre + next ); 
                visitTimeMap.putIfAbsent(uuid, time);     
            }
        );
        int yesterdayPv = visitorLogList.size();
        int yesterdayUv = pvMap.size();
        LocalDateTime yesterday = LocalDate.now().minusDays(1).atStartOfDay();
        
        //保存到每日pv、uv表
        visitPerDayService.save(VisitPerDay.builder()
                                           .pv(yesterdayPv)
                                           .uv(yesterdayUv)
                                           .date(yesterday)
                                           .build());
        //存总表
        visitPerDayService.saveTotal(yesterdayPv, yesterdayUv);

        //更新访客记录表的pv
        pvMap.forEach(
            (uuid, pv) -> {
                visitorService.update(
                    new UpdateWrapper<Visitor>()
                            .eq("uuid", uuid)
                            .set("gmt_update", visitTimeMap.get(uuid))
                            .setSql("view = view + " + pv)
                );
            }
        );

        //更新访客地图UV
        List<String> addressList = visitorService.getVisitorAddressYesterday();
        Map<String, Integer> visitorAddressMap = new HashMap<>(512);
        addressList.forEach(
            address -> {
                if(address.startsWith("中国")) {
                    String[] addr = address.split("\\|");
                    if(addr.length == 5) {
                        String res = addr[2];
                        res = res.replaceFirst("省", "");
                        res = res.replaceFirst("市", "");
                        visitorAddressMap.merge(res, 1, (pre, next) -> pre + next);
                    }
                }

            }
        );
        visitorAddressMap.forEach(
            (address, uv) -> {
                visitorAddressService.update(
                    new UpdateWrapper<VisitorAddress>()
                            .eq("address", address)
                            .setSql("uv = uv + " + uv)
                );
            }
        );
        redisUtil.clearVisitorCache();
    }
}
