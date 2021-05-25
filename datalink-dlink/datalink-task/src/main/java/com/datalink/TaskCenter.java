package com.datalink;

import com.datalink.base.ribbon.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description 作业中心
 * @Author wenmo
 * @Date 2021/5/21
 */
@EnableFeignClients
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignInterceptor
@SpringBootApplication
public class TaskCenter {
    public static void main(String[] args) {
        SpringApplication.run(TaskCenter.class, args);
    }
}
