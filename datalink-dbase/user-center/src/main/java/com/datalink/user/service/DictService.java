package com.datalink.user.service;

import com.datalink.base.model.Result;
import com.datalink.user.entity.Dict;
import com.datalink.db.mybatis.service.ISuperService;
/**
 * 扩展字典 服务类
 *
 * @author wenmo
 * @since 2021-05-09
 */
public interface DictService extends ISuperService<Dict> {

    void saveDict(Dict dict) throws Exception;

    Result saveOrUpdateDict(Dict dict) throws Exception;
}
