package com.datalink.task.dao;

import com.datalink.task.entity.Task;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务 Mapper 接口
 * @author wenmo
 * @since 2021-05-24
 */
@Mapper
public interface TaskMapper extends SuperMapper<Task> {

}
