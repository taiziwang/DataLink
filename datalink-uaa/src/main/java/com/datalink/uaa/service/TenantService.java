package com.datalink.uaa.service;

import com.datalink.base.model.Result;
import com.datalink.uaa.entity.Tenant;
import com.datalink.db.mybatis.service.ISuperService;
/**
 * 租户信息 服务类
 *
 * @author wenmo
 * @since 2021-05-10
 */
public interface TenantService extends ISuperService<Tenant> {

    void saveTenant(Tenant tenant) throws Exception;

    Result saveOrUpdateTenant(Tenant tenant) throws Exception;
}
