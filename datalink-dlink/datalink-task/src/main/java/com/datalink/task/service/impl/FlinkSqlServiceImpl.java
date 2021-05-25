package com.datalink.task.service.impl;

import com.datalink.base.model.Result;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.task.entity.FlinkSql;
import com.datalink.task.dao.FlinkSqlMapper;
import com.datalink.task.service.FlinkSqlService;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * FlinkSql 服务实现类
 *
 * @author wenmo
 * @since 2021-05-24
 */
@Service
public class FlinkSqlServiceImpl extends SuperServiceImpl<FlinkSqlMapper, FlinkSql> implements FlinkSqlService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveFlinkSql(@Validated({Save.class}) FlinkSql flinkSql) throws Exception {
        return super.save(flinkSql);
    }

    @Override
    @Transactional
    public Result saveOrUpdateFlinkSql(FlinkSql flinkSql) throws Exception {
        boolean result;
        if (flinkSql.getId() == null) {
            result = this.saveFlinkSql(flinkSql);
            return result ? Result.succeed("新增成功") : Result.failed("新增失败");
        } else {
            result = this.updateById(flinkSql);
            return result ? Result.succeed("修改成功") : Result.failed("修改失败");
        }
    }

}
