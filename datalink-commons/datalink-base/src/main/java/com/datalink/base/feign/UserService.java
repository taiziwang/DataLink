package com.datalink.base.feign;

import com.datalink.base.constant.ServiceNameConstant;
import com.datalink.base.feign.fallback.UserServiceFallbackFactory;
import com.datalink.base.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * UserService
 *
 * @author wenmo
 * @since 2021/5/10 20:12
 */
@FeignClient(name = ServiceNameConstant.USER_SERVICE, fallbackFactory = UserServiceFallbackFactory.class, decode404 = true)
public interface UserService {
    /**
     * feign rpc访问远程/users/{username}接口
     * 查询用户实体对象User
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/users/name/{username}")
    User selectByUsername(@PathVariable("username") String username);
}
