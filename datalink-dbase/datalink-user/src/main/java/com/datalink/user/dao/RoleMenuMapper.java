package com.datalink.user.dao;

import com.datalink.base.model.Menu;
import com.datalink.user.entity.RoleMenu;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 菜单角色关系 Mapper 接口
 * @author wenmo
 * @since 2021-05-10
 */
@Mapper
public interface RoleMenuMapper extends SuperMapper<RoleMenu> {
    List<Menu> findMenusByRoleIds(@Param("roleIds") Set<Integer> roleIds, @Param("type") Integer type);

    List<Menu> findMenusByRoleCodes(@Param("roleCodes") Set<String> roleCodes, @Param("type") Integer type);
}
