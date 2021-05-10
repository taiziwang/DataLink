package com.datalink.db.mybatis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datalink.base.model.PageResult;
import com.datalink.base.exception.IdempotencyException;
import com.datalink.base.exception.LockException;
import com.datalink.base.lock.DistributedLock;
import com.datalink.base.lock.ZLock;
import com.datalink.db.mybatis.mapper.SuperMapper;
import com.datalink.db.mybatis.service.ISuperService;
import com.datalink.db.mybatis.util.CTableUtil;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * service父类实现
 *
 * @author wenmo
 * @since 2021/5/3 19:43
 */
public class SuperServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements ISuperService<T> {
    @Override
    public boolean saveIdempotency(T entity, DistributedLock locker, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception {
        if (locker == null) {
            throw new LockException("DistributedLock为空");
        }
        if (StrUtil.isEmpty(lockKey)) {
            throw new LockException("lockKey为空");
        }
        try (
                ZLock lock = locker.tryLock(lockKey, 10, 60, TimeUnit.SECONDS);
        ) {
            if (lock != null) {
                int count = super.count(countWrapper);
                if (count == 0) {
                    return super.save(entity);
                } else {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    throw new IdempotencyException(msg);
                }
            } else {
                throw new LockException("锁等待超时");
            }
        }
    }

    @Override
    public boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) throws Exception {
        return saveIdempotency(entity, lock, lockKey, countWrapper, null);
    }

    @Override
    public boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StrUtil.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    return this.saveIdempotency(entity, lock, lockKey, countWrapper, msg);
                } else {
                    return updateById(entity);
                }
            } else {
                throw ExceptionUtils.mpe("错误:  无法找到 @TableId.");
            }
        }
        return false;
    }

    @Override
    public boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) throws Exception {
        return this.saveOrUpdateIdempotency(entity, lock, lockKey, countWrapper, null);
    }

    @Override
    public PageResult<T> selectForCTable(JsonNode para) {
        Integer pagesize = para.has("page") ? para.get("page").asInt() : 1;
        Integer limit = para.has("limit") ? para.get("limit").asInt() : 10;
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        CTableUtil.autoQueryAndSetFormParaCustom(para, queryWrapper);
        Page<T> page = new Page<>(pagesize, limit);
        List<T> list = baseMapper.selectForCTable(page, queryWrapper);
        return PageResult.<T>builder().data(list).code(0).count(page.getTotal()).build();
    }

    @Override
    public PageResult<T> listAllForCTable(JsonNode para) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        CTableUtil.autoQueryAndSetFormParaCustom(para, queryWrapper);
        List<T> list = baseMapper.selectForCTable(null, queryWrapper);
        return PageResult.<T>builder().data(list).code(200).build();
    }
}
