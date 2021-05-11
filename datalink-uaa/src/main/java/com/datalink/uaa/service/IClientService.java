package com.datalink.uaa.service;

import com.datalink.base.model.PageResult;
import com.datalink.base.model.Result;
import com.datalink.db.mybatis.service.ISuperService;
import com.datalink.uaa.entity.Client;

import java.util.Map;

/**
 * IClientService
 *
 * @author wenmo
 * @since 2021/5/11
 */
public interface IClientService extends ISuperService<Client> {
    Result saveClient(Client clientDto) throws Exception;

    /**
     * 查询应用列表
     * @param params
     * @param isPage 是否分页
     */
    PageResult<Client> listClient(Map<String, Object> params, boolean isPage);

    void delClient(long id);
}
