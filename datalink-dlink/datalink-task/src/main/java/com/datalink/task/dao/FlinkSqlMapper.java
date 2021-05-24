package com.datalink.task.dao;

import com.datalink.task.entity.FlinkSql;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * FlinkSql Mapper 接口
 * @author wenmo
 * @since 2021-05-24
 */
@Mapper
public interface FlinkSqlMapper extends SuperMapper<FlinkSql> {

}
