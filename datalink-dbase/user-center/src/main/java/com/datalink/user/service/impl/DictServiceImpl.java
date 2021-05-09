package com.datalink.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datalink.base.model.Result;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.db.mybatis.lock.DistributedLock;
import com.datalink.user.entity.Dict;
import com.datalink.user.dao.DictMapper;
import com.datalink.user.service.DictService;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 扩展字典 服务实现类
 *
 * @author wenmo
 * @since 2021-05-09
 */
@Service
public class DictServiceImpl extends SuperServiceImpl<DictMapper, Dict> implements DictService {
    private final static String LOCK_KEY_ROLECODE = "code:";

    @Autowired
    private DistributedLock lock;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveDict(@Validated({Save.class}) Dict dict) throws Exception {
        String code = dict.getCode();
        super.saveIdempotency(dict, lock
        , LOCK_KEY_ROLECODE+code, new QueryWrapper<Dict>().eq("code", code), "已存在");
    }

    @Override
    @Transactional
    public Result saveOrUpdateDict(Dict dict) throws Exception {
        if (dict.getId() == null) {
            this.saveDict(dict);
        } else {
            baseMapper.updateById(dict);
        }
        return Result.succeed("操作成功");
    }

}
