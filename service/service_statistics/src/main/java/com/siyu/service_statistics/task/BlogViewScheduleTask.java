package com.siyu.service_statistics.task;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.siyu.common_static.constant.RedisConstant;
import com.siyu.common_utils.RedisUtil.RedisUtil;
import com.siyu.service_statistics.entity.Blog;
import com.siyu.service_statistics.service.BlogService;

@Configuration
@EnableScheduling
public class BlogViewScheduleTask {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BlogService blogService;

    // 每日执行一次
    @Scheduled(cron = "0 0 0 * * ?")
    public void redisToDatabaseTask() {
        System.out.println("执行了");
        Map<Object, Object> map = redisUtil.getValueFromHash(RedisConstant.BLOG_VIEW_HASH_KEY);
        redisUtil.clearBlogViewCache();
        redisUtil.clearBlogCache();
        map.forEach(
            (o1, o2) -> {
                String id = (String) o1;
                Integer view = (Integer) o2;
                blogService.update(
                    new UpdateWrapper<Blog>()
                        .eq("id", id)
                        .setSql("view = view + " + view)
                );
            }
        );
    }
}
