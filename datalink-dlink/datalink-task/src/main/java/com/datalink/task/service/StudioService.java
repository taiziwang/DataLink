package com.datalink.task.service;

import com.datalink.flink.result.RunResult;
import com.datalink.task.dto.FlinkSqlExecuteDTO;

/**
 * StudioService
 *
 * @author wenmo
 * @since 2021/5/26 23:15
 */
public interface StudioService {
    RunResult executeSql(FlinkSqlExecuteDTO flinkSqlExecuteDTO);
}
