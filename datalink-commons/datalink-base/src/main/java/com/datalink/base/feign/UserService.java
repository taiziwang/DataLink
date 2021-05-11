package com.datalink.base.feign;

import com.datalink.base.constant.ServiceNameConstant;
import com.datalink.base.feign.fallback.UserServiceFallbackFactory;
import com.datalink.base.model.LoginAppUser;
import com.datalink.base.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    /**
     * feign rpc访问远程/users-anon/login接口
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/users-anon/login", params = "username")
    LoginAppUser findByUsername(@RequestParam("username") String username);
    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     */
    @GetMapping(value = "/users-anon/mobile", params = "mobile")
    LoginAppUser findByMobile(@RequestParam("mobile") String mobile);

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     */
    @GetMapping(value = "/users-anon/openId", params = "openId")
    LoginAppUser findByOpenId(@RequestParam("openId") String openId);
}
