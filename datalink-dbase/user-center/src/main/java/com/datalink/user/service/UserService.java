package com.datalink.user.service;

import com.datalink.base.model.Result;
import com.datalink.base.model.User;
import com.datalink.db.mybatis.service.ISuperService;

/**
 * 服务类
 *
 * @author wenmo
 * @since 2021-05-03
 */
public interface UserService extends ISuperService<User> {

    void saveUser(User user) throws Exception;

    Result saveOrUpdateUser(User user) throws Exception;
}
