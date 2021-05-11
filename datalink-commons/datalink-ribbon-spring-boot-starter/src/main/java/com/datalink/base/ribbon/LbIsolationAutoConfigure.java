package com.datalink.base.ribbon;

import com.datalink.base.ribbon.config.RuleConfigure;
import com.datalink.base.ribbon.constants.ConfigConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * LbIsolationAutoConfigure
 *
 * @author wenmo
 * @since 2021/5/11
 */
@ConditionalOnProperty(value = ConfigConstant.CONFIG_RIBBON_ISOLATION_ENABLED, havingValue = "true")
@RibbonClients(defaultConfiguration = {RuleConfigure.class})
public class LbIsolationAutoConfigure {

}
