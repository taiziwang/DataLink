package com.datalink.user.service;

import com.datalink.base.model.Result;
import com.datalink.base.model.Menu;
import com.datalink.user.entity.RoleMenu;
import com.datalink.db.mybatis.service.ISuperService;

import java.util.List;
import java.util.Set;

/**
 * 菜单角色关系 服务类
 *
 * @author wenmo
 * @since 2021-05-10
 */
public interface RoleMenuService extends ISuperService<RoleMenu> {

    void saveRoleMenu(RoleMenu roleMenu) throws Exception;

    Result saveOrUpdateRoleMenu(RoleMenu roleMenu) throws Exception;

    List<Menu> findMenusByRoleIds(Set<Integer> roleIds, Integer type);

    List<Menu> findMenusByRoleCodes(Set<String> roleCodes, Integer type);
}
