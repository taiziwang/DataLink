package com.datalink.user.service;

import com.datalink.base.model.Result;
import com.datalink.base.model.Role;
import com.datalink.user.entity.RoleUser;
import com.datalink.db.mybatis.service.ISuperService;

import java.util.List;

/**
 * 用户角色关系 服务类
 *
 * @author wenmo
 * @since 2021-05-06
 */
public interface RoleUserService extends ISuperService<RoleUser> {

    void saveRoleUser(RoleUser roleUser) throws Exception;

    Result saveOrUpdateRoleUser(RoleUser roleUser) throws Exception;

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @return
     */
    List<Role> findRolesByUserId(Integer userId);
    /**
     * 根据用户ids 获取
     *
     * @param userIds
     * @return
     */
    List<Role> findRolesByUserIds(List<Long> userIds);
}
