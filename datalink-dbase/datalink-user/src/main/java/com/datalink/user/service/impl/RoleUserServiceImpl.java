package com.datalink.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datalink.base.model.Result;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.db.mybatis.lock.DistributedLock;
import com.datalink.user.entity.RoleUser;
import com.datalink.user.dao.RoleUserMapper;
import com.datalink.user.service.RoleUserService;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 用户角色关系 服务实现类
 *
 * @author wenmo
 * @since 2021-05-06
 */
@Service
public class RoleUserServiceImpl extends SuperServiceImpl<RoleUserMapper, RoleUser> implements RoleUserService {
    private final static String LOCK_KEY_ROLECODE = "code:";

    /*@Autowired
    private DistributedLock lock;*/

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRoleUser(@Validated({Save.class}) RoleUser roleUser) throws Exception {
        baseMapper.insert(roleUser);
       /* String code = roleUser.getUserId()+"_"+roleUser.getRoleId();
        super.saveIdempotency(roleUser, lock
        , LOCK_KEY_ROLECODE+code, new QueryWrapper<RoleUser>().eq("code", code), "已存在");*/
    }

    @Override
    @Transactional
    public Result saveOrUpdateRoleUser(RoleUser roleUser) throws Exception {
        if (roleUser.getId() == null) {
            this.saveRoleUser(roleUser);
        } else {
            baseMapper.updateById(roleUser);
        }
        return Result.succeed("操作成功");
    }

}
