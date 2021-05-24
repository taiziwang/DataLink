package com.datalink.db.mybatis.config;

import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.datalink.base.context.TenantContextHolder;
import com.datalink.base.properties.TenantProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @Description 多租户配置
 * @Author wenmo
 * @Date 2021/5/3 14:38
 */
@EnableConfigurationProperties(TenantProperties.class)
public class TenantConfigure {
    @Autowired
    private TenantProperties tenantProperties;

    @Bean
    public TenantHandler tenantHandler() {
        return new TenantHandler() {

            @Override
            public Expression getTenantId(boolean where) {
                String tenant = TenantContextHolder.getTenant();
                if (tenant != null) {
                    return new StringValue(TenantContextHolder.getTenant());
                }
                return new NullValue();
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                return tenantProperties.getIgnoreTables().stream().anyMatch(
                        (e) -> e.equalsIgnoreCase(tableName)
                );
            }
        };
    }

    /**
     * 过滤不需要根据租户隔离的MappedStatement
     */
    @Bean
    public ISqlParserFilter sqlParserFilter() {
        return metaObject -> {
            MappedStatement mappedStatement = SqlParserHelper.getMappedStatement(metaObject);
            return tenantProperties.getIgnoreSqls().stream().anyMatch(
                    (e) -> e.equalsIgnoreCase(mappedStatement.getId())
            );
        };
    }
}
