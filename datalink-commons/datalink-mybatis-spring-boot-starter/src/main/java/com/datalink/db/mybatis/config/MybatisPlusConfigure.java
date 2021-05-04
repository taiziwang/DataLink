package com.datalink.db.mybatis.config;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.datalink.db.mybatis.handler.DateMetaObjectHandler;
import com.datalink.db.mybatis.properties.MybatisPlusFillProperties;
import com.datalink.db.mybatis.properties.TenantProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @Description MybatisPlus 配置
 * @Author wenmo
 * @Date 2021/5/3 14:52
 */
@EnableConfigurationProperties(MybatisPlusFillProperties.class)
public class MybatisPlusConfigure {
    @Autowired
    private TenantHandler tenantHandler;

    @Autowired
    private ISqlParserFilter sqlParserFilter;

    @Autowired
    private TenantProperties tenantProperties;

    @Autowired
    private MybatisPlusFillProperties autoFillProperties;

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        if (tenantProperties.getEnable()) {
            TenantSqlParser tenantSqlParser = new TenantSqlParser()
                    .setTenantHandler(tenantHandler);
            paginationInterceptor.setSqlParserList(CollUtil.<ISqlParser>toList(tenantSqlParser));
            paginationInterceptor.setSqlParserFilter(sqlParserFilter);
        }
        return paginationInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "datalink.mybatis-plus.fill", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MetaObjectHandler metaObjectHandler() {
        return new DateMetaObjectHandler(autoFillProperties);
    }
}
