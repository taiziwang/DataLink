package com.datalink.gateway.auth;

import com.datalink.base.utils.WebfluxResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * JsonAuthenticationEntryPoint
 * 401未授权异常处理，转换为JSON
 *
 * @author wenmo
 * @since 2021/5/12
 */
@Slf4j
public class JsonAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        return WebfluxResponseUtil.responseFailed(exchange, HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }
}
