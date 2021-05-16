package com.datalink;

import com.datalink.base.ribbon.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description 用户中心
 * @Author wenmo
 * @Date 2021/5/3 15:22
 */
//@EnableFeignClients
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignInterceptor
@SpringBootApplication
public class UserCenter {
    public static void main(String[] args) {
        SpringApplication.run(UserCenter.class, args);
    }
}
