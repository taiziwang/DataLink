package com.datalink.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * TraceProperties
 *
 * @author wenmo
 * @since 2021/5/11
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "datalink.trace")
@RefreshScope
public class TraceProperties {
    /**
     * 是否开启日志链路追踪
     */
    private Boolean enable = false;
}
