package com.datalink.log.properties;

import com.zaxxer.hikari.HikariConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * LogDbProperties
 *
 * @author wenmo
 * @since 2021/5/11
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "datalink.audit-log.datasource")
public class LogDbProperties extends HikariConfig {
}
