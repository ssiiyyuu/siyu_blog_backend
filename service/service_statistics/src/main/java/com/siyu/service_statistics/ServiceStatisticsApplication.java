package com.siyu.service_statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.siyu.service_statistics.mapper")
@ComponentScan(basePackages = {"com.siyu"})
public class ServiceStatisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceStatisticsApplication.class, args);
	}

}
