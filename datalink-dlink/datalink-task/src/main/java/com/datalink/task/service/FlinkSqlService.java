package com.datalink.task.service;

import com.datalink.base.model.Result;
import com.datalink.task.entity.FlinkSql;
import com.datalink.db.mybatis.service.ISuperService;
/**
 * FlinkSql 服务类
 *
 * @author wenmo
 * @since 2021-05-24
 */
public interface FlinkSqlService extends ISuperService<FlinkSql> {

    boolean saveFlinkSql(FlinkSql flinkSql) throws Exception;

    Result saveOrUpdateFlinkSql(FlinkSql flinkSql) throws Exception;
}
