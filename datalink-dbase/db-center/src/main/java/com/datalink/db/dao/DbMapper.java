package com.datalink.db.dao;

import com.datalink.db.entity.Db;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * DB Mapper 接口
 * @author wenmo
 * @since 2021-05-09
 */
@Mapper
public interface DbMapper extends SuperMapper<Db> {

}
