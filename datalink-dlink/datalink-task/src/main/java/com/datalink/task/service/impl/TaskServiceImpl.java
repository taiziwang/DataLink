package com.datalink.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datalink.base.lock.DistributedLock;
import com.datalink.base.model.Result;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.task.entity.Task;
import com.datalink.task.dao.TaskMapper;
import com.datalink.task.service.TaskService;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 任务 服务实现类
 *
 * @author wenmo
 * @since 2021-05-24
 */
@Service
public class TaskServiceImpl extends SuperServiceImpl<TaskMapper, Task> implements TaskService {
    private final static String LOCK_KEY_CODE = "name:";

    @Autowired
    private DistributedLock lock;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveTask(@Validated({Save.class}) Task task) throws Exception {
        String code = task.getName();
        return super.saveIdempotency(task, lock
        , LOCK_KEY_CODE+code, new QueryWrapper<Task>().eq("name", code), "已存在");
    }

    @Override
    @Transactional
    public Result saveOrUpdateTask(Task task) throws Exception {
        boolean result;
        if (task.getId() == null) {
            result = this.saveTask(task);
            return result ? Result.succeed("新增成功") : Result.failed("新增失败");
        } else {
            result = this.updateById(task);
            return result ? Result.succeed("修改成功") : Result.failed("修改失败");
        }
    }

}
