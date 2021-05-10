package com.datalink.user.dao;

import com.datalink.base.model.Menu;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单 Mapper 接口
 * @author wenmo
 * @since 2021-05-10
 */
@Mapper
public interface MenuMapper extends SuperMapper<Menu> {

}
