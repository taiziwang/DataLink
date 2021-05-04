package com.datalink.user.dao;

import com.datalink.base.model.User;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口
 * @author wenmo
 * @since 2021-05-03
 */
@Mapper
public interface UserMapper extends SuperMapper<User> {

}
