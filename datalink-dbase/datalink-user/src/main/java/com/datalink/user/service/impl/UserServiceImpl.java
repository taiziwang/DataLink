package com.datalink.user.service.impl;

import cn.hutool.core.util.StrUtil;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final static String LOCK_KEY_USERNAME = "username:";

    @Autowired
    private DistributedLock lock;

    @Resource
    private RoleUserService roleUserService;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUser(@Validated({Save.class}) User user) throws Exception {
        user.setIsDelete(false);
        String code = user.getUsername();
        return super.saveIdempotency(user, lock
        , LOCK_KEY_USERNAME+code, new QueryWrapper<User>().eq("username", code), "已存在");
    }

    @Override
    @Transactional
    public Result saveOrUpdateUser(User user) throws Exception {
        boolean result;
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(CommonConstant.DEF_USER_PASSWORD));
            user.setEnabled(Boolean.TRUE);
            result = this.saveUser(user);
            return result ? Result.succeed("新增成功") : Result.failed("新增失败");
        }else{
            result = this.updateById(user);
            return result ? Result.succeed("修改成功") : Result.failed("修改失败");
        }
    }

    @Override
    public LoginAppUser findByUsername(String username) {
        User user = this.selectByUsername(username);
        return getLoginAppUser(user);
    }

    @Override
    public LoginAppUser findByMobile(String username) {
        User user = this.selectByMobile(username);
        return getLoginAppUser(user);
    }

    @Override
    public LoginAppUser findByOpenId(String username) {
        User user = this.selectByOpenId(username);
        return getLoginAppUser(user);
    }

    @Override
    public User selectByUsername(String username) {
        List<User> users = baseMapper.selectList(
                new QueryWrapper<User>().eq("username", username)
        );
        return getUser(users);
    }

    @Override
    public User selectByMobile(String mobile) {
        List<User> users = baseMapper.selectList(
                new QueryWrapper<User>().eq("mobile", mobile)
        );
        return getUser(users);
    }

    @Override
    public User selectByOpenId(String openId) {
        List<User> users = baseMapper.selectList(
                new QueryWrapper<User>().eq("open_id", openId)
        );
        return getUser(users);
    }
    
    @Override
    public LoginAppUser getLoginAppUser(User user) {
        if (user != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(user, loginAppUser);

            List<Role> sysRoles = roleUserService.findRolesByUserId(user.getId());
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
