package com.datalink.task.service;

import com.datalink.base.model.Result;
import com.datalink.flink.result.SubmitResult;
import com.datalink.task.entity.Task;
import com.datalink.db.mybatis.service.ISuperService;
/**
 * 任务 服务类
 *
 * @author wenmo
 * @since 2021-05-24
 */
public interface TaskService extends ISuperService<Task> {

    boolean saveTask(Task task) throws Exception;

    Result saveOrUpdateTask(Task task) throws Exception;

    SubmitResult submitByTaskId(Integer id) throws Exception;

}
