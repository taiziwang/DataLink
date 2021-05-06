package com.datalink.user.dao;

import com.datalink.user.entity.RoleUser;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关系 Mapper 接口
 * @author wenmo
 * @since 2021-05-06
 */
@Mapper
public interface RoleUserMapper extends SuperMapper<RoleUser> {

}
