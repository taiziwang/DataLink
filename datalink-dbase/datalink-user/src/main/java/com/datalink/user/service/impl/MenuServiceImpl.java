package com.datalink.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datalink.base.model.Result;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.base.lock.DistributedLock;
import com.datalink.base.model.Menu;
import com.datalink.user.dao.MenuMapper;
import com.datalink.user.service.MenuService;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 菜单 服务实现类
 *
 * @author wenmo
 * @since 2021-05-10
 */
@Service
public class MenuServiceImpl extends SuperServiceImpl<MenuMapper, Menu> implements MenuService {
    private final static String LOCK_KEY_ROLECODE = "code:";

    @Autowired
    private DistributedLock lock;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMenu(@Validated({Save.class}) Menu menu) throws Exception {
        String code = menu.getName();
        super.saveIdempotency(menu, lock
        , LOCK_KEY_ROLECODE+code, new QueryWrapper<Menu>().eq("name", code), "已存在");
    }

    @Override
    @Transactional
    public Result saveOrUpdateMenu(Menu menu) throws Exception {
        if (menu.getId() == null) {
            this.saveMenu(menu);
        } else {
            baseMapper.updateById(menu);
        }
        return Result.succeed("操作成功");
    }

}
