package com.datalink.base.ribbon.config;

import cn.hutool.core.util.StrUtil;
import com.datalink.base.constant.SecurityConstant;
import com.datalink.base.context.TenantContextHolder;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * FeignInterceptorConfig
 * feign拦截器，只包含基础数据
 *
 * @author wenmo
 * @since 2021/5/11
 */
public class FeignInterceptorConfig {
    /**
     * 使用feign client访问别的微服务时，将上游传过来的client等信息放入header传递给下一个服务
     */
    @Bean
    public RequestInterceptor baseFeignInterceptor() {
        return template -> {
            //传递client
            String tenant = TenantContextHolder.getTenant();
            if (StrUtil.isNotEmpty(tenant)) {
                template.header(SecurityConstant.TENANT_HEADER, tenant);
            }
        };
    }
}
