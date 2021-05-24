package com.datalink.base.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 多租户配置项
 * @Author wenmo
 * @Date 2021/5/3 14:43
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "datalink.tenant")
@RefreshScope
public class TenantProperties {

    private Boolean enable = false;

    private List<String> ignoreTables = new ArrayList<>();

    private List<String> ignoreSqls = new ArrayList<>();
}
