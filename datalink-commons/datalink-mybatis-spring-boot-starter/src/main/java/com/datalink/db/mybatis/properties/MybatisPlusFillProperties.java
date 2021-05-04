package com.datalink.db.mybatis.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @Description Mybatis-Plus 配置项
 * @Author wenmo
 * @Date 2021/5/3 13:55
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "datalink.mybatis-plus.fill")
@RefreshScope
public class MybatisPlusFillProperties {

    private Boolean enabled = true;

    private Boolean enableInsertFill = true;

    private Boolean enableUpdateFill = true;

    private String createTimeField = "createTime";

    private String updateTimeField = "updateTime";
}
