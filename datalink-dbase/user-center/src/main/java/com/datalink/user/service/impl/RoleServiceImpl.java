package com.datalink.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datalink.base.model.Result;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.db.mybatis.lock.DistributedLock;
import com.datalink.user.entity.Role;
import com.datalink.user.dao.RoleMapper;
import com.datalink.user.service.RoleService;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 角色 服务实现类
 *
 * @author wenmo
 * @since 2021-05-06
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements RoleService {
    private final static String LOCK_KEY_ROLECODE = "code:";

    /*@Autowired
    private DistributedLock lock;*/

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRole(@Validated({Save.class}) Role role) throws Exception {
        baseMapper.insert(role);
        /*String code = role.getCode();
        super.saveIdempotency(role, lock
        , LOCK_KEY_ROLECODE+code, new QueryWrapper<Role>().eq("code", code), "已存在");*/
    }

    @Override
    @Transactional
    public Result saveOrUpdateRole(Role role) throws Exception {
        if (role.getId() == null) {
            this.saveRole(role);
        } else {
            baseMapper.updateById(role);
        }
        return Result.succeed("操作成功");
    }

}
