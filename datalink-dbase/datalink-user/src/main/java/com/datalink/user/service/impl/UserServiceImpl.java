package com.datalink.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datalink.base.constant.CommonConstant;
import com.datalink.base.lock.DistributedLock;
import com.datalink.base.model.*;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import com.datalink.user.dao.RoleMenuMapper;
import com.datalink.user.dao.UserMapper;
import com.datalink.base.model.Menu;
import com.datalink.user.service.RoleUserService;
import com.datalink.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  用户服务实现类
 *
 * @author wenmo
 * @since 2021-05-03
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {
    private final static String LOCK_KEY_ROLECODE = "username:";

    @Autowired
    private DistributedLock lock;

    @Resource
    private RoleUserService roleUserService;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUser(@Validated({Save.class}) User user) throws Exception {
        user.setIsDelete(false);
        String code = user.getUsername();
        super.saveIdempotency(user, lock
        , LOCK_KEY_ROLECODE+code, new QueryWrapper<User>().eq("username", code), "已存在");
    }

    @Override
    @Transactional
    public Result saveOrUpdateUser(User user) throws Exception {
        if (user.getId() == null) {
            this.saveUser(user);
        } else {
            baseMapper.updateById(user);
        }
        return Result.succeed("操作成功");
    }

    @Override
    public LoginAppUser findByUsername(String username) {
        User sysUser = this.selectByUsername(username);
        return getLoginAppUser(sysUser);
    }

    @Override
    public User selectByUsername(String username) {
        List<User> users = baseMapper.selectList(
                new QueryWrapper<User>().eq("username", username)
        );
        return getUser(users);
    }

    @Override
    public LoginAppUser getLoginAppUser(User sysUser) {
        if (sysUser != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(sysUser, loginAppUser);

            List<Role> sysRoles = roleUserService.findRolesByUserId(sysUser.getId());
            // 设置角色
            loginAppUser.setRoles(sysRoles);

            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<Integer> roleIds = sysRoles.parallelStream().map(SuperEntity::getId).collect(Collectors.toSet());
                List<Menu> menus = roleMenuMapper.findMenusByRoleIds(roleIds, CommonConstant.PERMISSION);
                if (!CollectionUtils.isEmpty(menus)) {
                    Set<String> permissions = menus.parallelStream().map(p -> p.getPath())
                            .collect(Collectors.toSet());
                    // 设置权限集合
                    loginAppUser.setPermissions(permissions);
                }
            }
            return loginAppUser;
        }
        return null;
    }

    private User getUser(List<User> users) {
        User user = null;
        if (users != null && !users.isEmpty()) {
            user = users.get(0);
        }
        return user;
    }

}
