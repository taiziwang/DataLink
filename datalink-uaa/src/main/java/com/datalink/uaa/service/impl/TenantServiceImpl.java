package com.datalink.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datalink.base.model.Result;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.db.mybatis.lock.DistributedLock;
import com.datalink.uaa.entity.Tenant;
import com.datalink.uaa.dao.TenantMapper;
import com.datalink.uaa.service.TenantService;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 租户信息 服务实现类
 *
 * @author wenmo
 * @since 2021-05-10
 */
@Service
public class TenantServiceImpl extends SuperServiceImpl<TenantMapper, Tenant> implements TenantService {
    private final static String LOCK_KEY_ROLECODE = "code:";

    @Autowired
    private DistributedLock lock;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveTenant(@Validated({Save.class}) Tenant tenant) throws Exception {
        String code = tenant.getCode();
        super.saveIdempotency(tenant, lock
        , LOCK_KEY_ROLECODE+code, new QueryWrapper<Tenant>().eq("code", code), "已存在");
    }

    @Override
    @Transactional
    public Result saveOrUpdateTenant(Tenant tenant) throws Exception {
        if (tenant.getId() == null) {
            this.saveTenant(tenant);
        } else {
            baseMapper.updateById(tenant);
        }
        return Result.succeed("操作成功");
    }

}
