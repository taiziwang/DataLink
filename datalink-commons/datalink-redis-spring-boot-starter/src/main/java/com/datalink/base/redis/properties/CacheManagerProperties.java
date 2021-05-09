package com.datalink.base.redis.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * CacheManagerProperties
 *
 * @author wenmo
 * @since 2021/5/9 23:22
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "datalink.cache-manager")
public class CacheManagerProperties {
    private List<CacheConfig> configs;

    @Setter
    @Getter
    public static class CacheConfig {
        /**
         * cache key
         */
        private String key;
        /**
         * 过期时间，sec
         */
        private long second = 60;
    }
}
