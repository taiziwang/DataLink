package com.datalink.user.dao;

import com.datalink.base.model.Role;
import com.datalink.user.entity.RoleUser;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色关系 Mapper 接口
 * @author wenmo
 * @since 2021-05-06
 */
@Mapper
public interface RoleUserMapper extends SuperMapper<RoleUser> {

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @return
     */
    List<Role> findRolesByUserId(@Param("userId") Integer userId);
    /**
     * 根据用户ids 获取
     *
     * @param userIds
     * @return
     */
    @Select("<script>select r.*,ru.user_id from dbase_role_user ru inner join dbase_role r on r.id = ru.role_id where ru.user_id IN " +
            " <foreach item='item' index='index' collection='list' open='(' separator=',' close=')'> " +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<Role> findRolesByUserIds(List<Long> userIds);
}
