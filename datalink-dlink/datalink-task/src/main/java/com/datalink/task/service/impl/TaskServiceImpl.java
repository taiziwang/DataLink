package com.datalink.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.datalink.base.lock.DistributedLock;
import com.datalink.base.model.Result;
import com.datalink.base.redis.template.RedisRepository;
import com.datalink.db.mybatis.annotation.Save;
import com.datalink.flink.cluster.FlinkCluster;
import com.datalink.flink.executor.Executor;
import com.datalink.flink.executor.ExecutorSetting;
import com.datalink.flink.job.JobManager;
import com.datalink.flink.result.SubmitResult;
import com.datalink.task.entity.FlinkSql;
import com.datalink.task.entity.Task;
import com.datalink.task.dao.TaskMapper;
import com.datalink.task.service.FlinkSqlService;
import com.datalink.task.service.TaskService;
import com.datalink.db.mybatis.service.impl.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private FlinkSqlService flinkSqlService;
    @Autowired
    private RedisRepository redisRepository;
    @Value("${datalink.flink.host}")
    private String hosts;
    @Value("${datalink.flink.port}")
    private Integer port;

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

    @Override
    public SubmitResult submitByTaskId(Integer id) throws Exception {
        List<FlinkSql> flinkSqls = flinkSqlService.list(new QueryWrapper<FlinkSql>().eq("task_id", id).eq("enabled", 1).orderByAsc("sql_index"));
        JobManager jobManager = new JobManager(FlinkCluster.getFlinkJobManagerIP(hosts,redisRepository),port);
        List<String> sqls = new ArrayList<>();
        for (int i = 0; i < flinkSqls.size(); i++) {
            sqls.add(flinkSqls.get(i).getStatement());
        }
        return jobManager.submit(sqls, new ExecutorSetting(Executor.REMOTE));
    }

}
