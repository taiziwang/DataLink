package com.datalink.base.config;

import com.datalink.base.feign.UserService;
import com.datalink.base.resolver.ClientArgumentResolver;
import com.datalink.base.resolver.TokenArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * DefaultWebMvcConfig
 *
 * @author wenmo
 * @since 2021/5/10 20:50
 */
public class DefaultWebMvcConfig implements WebMvcConfigurer {
    @Lazy
    @Autowired
    private UserService userService;

    /**
     * Token参数解析
     *
     * @param argumentResolvers 解析类
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
        argumentResolvers.add(new TokenArgumentResolver(userService));
        //注入应用信息
        argumentResolvers.add(new ClientArgumentResolver());
    }
}
