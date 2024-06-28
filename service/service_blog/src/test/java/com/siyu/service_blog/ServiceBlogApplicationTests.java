package com.siyu.service_blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.siyu.service_blog.util.IpUtil;

@SpringBootTest
class ServiceBlogApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    void redisTest() {
        System.out.println(IpUtil.getIpAddress("36.101.29.105"));
    }

}
