package com.datalink.user.dao;

import com.datalink.base.model.Role;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色 Mapper 接口
 * @author wenmo
 * @since 2021-05-06
 */
@Mapper
public interface RoleMapper extends SuperMapper<Role> {

}
