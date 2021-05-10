package com.datalink.base.feign.fallback;

import com.datalink.base.feign.UserService;
import com.datalink.base.model.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * UserServiceFallbackFactory
 *
 * @author wenmo
 * @since 2021/5/10 20:13
 */
@Slf4j
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {
            @Override
            public User selectByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                return new User();
            }
        };
    }
}
