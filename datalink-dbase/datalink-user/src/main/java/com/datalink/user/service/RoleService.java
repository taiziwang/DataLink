package com.datalink.user.service;

import com.datalink.base.model.Result;
import com.datalink.user.entity.Role;
import com.datalink.db.mybatis.service.ISuperService;
/**
 * 角色 服务类
 *
 * @author wenmo
 * @since 2021-05-06
 */
public interface RoleService extends ISuperService<Role> {

    void saveRole(Role role) throws Exception;

    Result saveOrUpdateRole(Role role) throws Exception;
}
