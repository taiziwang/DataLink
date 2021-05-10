package com.datalink.db.service;

import com.datalink.base.model.Result;
import com.datalink.db.entity.Db;
import com.datalink.db.mybatis.service.ISuperService;
/**
 * DB 服务类
 *
 * @author wenmo
 * @since 2021-05-09
 */
public interface DbService extends ISuperService<Db> {

    void saveDb(Db db) throws Exception;

    Result saveOrUpdateDb(Db db) throws Exception;
}
