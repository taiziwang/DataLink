package com.datalink.base.ribbon.filter;

import cn.hutool.core.util.StrUtil;
import com.datalink.base.constant.CommonConstant;
import com.datalink.base.context.LbIsolationContextHolder;
import com.datalink.base.ribbon.constants.ConfigConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LbIsolationFilter
 * 负载均衡隔离规则过滤器
 *
 * @author wenmo
 * @since 2021/5/11
 */
@ConditionalOnClass(Filter.class)
public class LbIsolationFilter extends OncePerRequestFilter {
    @Value("${" + ConfigConstant.CONFIG_RIBBON_ISOLATION_ENABLED + ":false}")
    private boolean enableIsolation;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !enableIsolation;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        try {
            String version = request.getHeader(CommonConstant.DATALINK_VERSION);
            if(StrUtil.isNotEmpty(version)){
                LbIsolationContextHolder.setVersion(version);
            }

            filterChain.doFilter(request, response);
        } finally {
            LbIsolationContextHolder.clear();
        }
    }
}
