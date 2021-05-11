package com.datalink.base.ribbon;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * FeignAutoConfigure
 *
 * @author wenmo
 * @since 2021/5/11
 */
public class FeignAutoConfigure {

    /**
     * Feign 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
