package com.datalink.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datalink.base.model.Result;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.base.lock.DistributedLock;
import com.datalink.base.model.Menu;
import com.datalink.user.entity.RoleMenu;
import com.datalink.user.dao.RoleMenuMapper;
import com.datalink.user.service.RoleMenuService;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;

/**
 * 菜单角色关系 服务实现类
 *
 * @author wenmo
 * @since 2021-05-10
 */
@Service
public class RoleMenuServiceImpl extends SuperServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    private final static String LOCK_KEY_ROLECODE = "code:";

    @Autowired
    private DistributedLock lock;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRoleMenu(@Validated({Save.class}) RoleMenu roleMenu) throws Exception {
        String code = roleMenu.getMenuId()+"_"+roleMenu.getRoleId();
        super.saveIdempotency(roleMenu, lock
        , LOCK_KEY_ROLECODE+code, new QueryWrapper<RoleMenu>().eq("menu_id", roleMenu.getMenuId()).eq("role_id", roleMenu.getRoleId()), "已存在");
    }

    @Override
    @Transactional
    public Result saveOrUpdateRoleMenu(RoleMenu roleMenu) throws Exception {
        if (roleMenu.getId() == null) {
            this.saveRoleMenu(roleMenu);
        } else {
            baseMapper.updateById(roleMenu);
        }
        return Result.succeed("操作成功");
    }

    @Override
    public List<Menu> findMenusByRoleIds(Set<Integer> roleIds, Integer type) {
        return baseMapper.findMenusByRoleIds(roleIds, type);
    }

    @Override
    public List<Menu> findMenusByRoleCodes(Set<String> roleCodes, Integer type) {
        return baseMapper.findMenusByRoleCodes(roleCodes, type);
    }

}
