package com.datalink.db.service.impl;

import com.datalink.base.model.Result;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.db.entity.Db;
import com.datalink.db.dao.DbMapper;
import com.datalink.db.service.DbService;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * DB 服务实现类
 *
 * @author wenmo
 * @since 2021-05-09
 */
@Service
public class DbServiceImpl extends SuperServiceImpl<DbMapper, Db> implements DbService {
    private final static String LOCK_KEY_ROLECODE = "code:";

    /*@Autowired
    private DistributedLock lock;*/

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveDb(@Validated({Save.class}) Db db) throws Exception {
        baseMapper.insert(db);
        /*String code = db.getCode();
        super.saveIdempotency(db, lock
        , LOCK_KEY_ROLECODE+code, new QueryWrapper<Db>().eq("code", code), "已存在");*/
    }

    @Override
    @Transactional
    public Result saveOrUpdateDb(Db db) throws Exception {
        if (db.getId() == null) {
            this.saveDb(db);
        } else {
            baseMapper.updateById(db);
        }
        return Result.succeed("操作成功");
    }

}
