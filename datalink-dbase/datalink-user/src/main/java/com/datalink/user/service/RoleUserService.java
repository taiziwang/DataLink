package com.datalink.user.service;

import com.datalink.base.model.Result;
import com.datalink.user.entity.RoleUser;
import com.datalink.db.mybatis.service.ISuperService;
/**
 * 用户角色关系 服务类
 *
 * @author wenmo
 * @since 2021-05-06
 */
public interface RoleUserService extends ISuperService<RoleUser> {

    void saveRoleUser(RoleUser roleUser) throws Exception;

    Result saveOrUpdateRoleUser(RoleUser roleUser) throws Exception;
}
