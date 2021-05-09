package com.datalink.user.service.impl;

import com.datalink.base.model.Result;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.db.mybatis.lock.DistributedLock;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import com.datalink.base.model.User;
import com.datalink.user.dao.UserMapper;
import com.datalink.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 *  服务实现类
 *
 * @author wenmo
 * @since 2021-05-03
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {
    private final static String LOCK_KEY_ROLECODE = "username:";

    /*@Autowired
    private DistributedLock lock;*/

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUser(@Validated({Save.class}) User user) throws Exception {
        user.setIsDelete(false);
        baseMapper.insert(user);
        /*String code = user.getNickname();
        super.saveIdempotency(user, lock
        , LOCK_KEY_ROLECODE+code, new QueryWrapper<User>().eq("username", code), "已存在");
    */}

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

}
