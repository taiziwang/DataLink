package com.datalink.task.service.impl;

import com.datalink.base.redis.template.RedisRepository;
import com.datalink.flink.cluster.FlinkCluster;
import com.datalink.flink.job.JobManager;
import com.datalink.flink.result.RunResult;
import com.datalink.task.dto.FlinkSqlExecuteDTO;
import com.datalink.task.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * StudioServiceImpl
 *
 * @author wenmo
 * @since 2021/5/26 23:15
 */
@Service
public class StudioServiceImpl implements StudioService {
    @Autowired
    private RedisRepository redisRepository;
    @Value("${datalink.flink.host}")
    private String hosts;
    @Value("${datalink.flink.port}")
    private Integer port;

    @Override
    public RunResult executeSql(FlinkSqlExecuteDTO flinkSqlExecuteDTO) {
        String jobHosts = flinkSqlExecuteDTO.getFlinkHost();
        if(jobHosts==null||"".equals(jobHosts)){
            jobHosts=hosts;
        }
        Integer jobPort = flinkSqlExecuteDTO.getPort();
        if(jobPort==null){
            jobPort=port;
        }
        JobManager jobManager = new JobManager(
                FlinkCluster.getFlinkJobManagerIP(jobHosts,redisRepository),jobPort,
                flinkSqlExecuteDTO.getSessionId(),flinkSqlExecuteDTO.getMaxRowNum());
        return jobManager.execute(flinkSqlExecuteDTO.getStatement());
    }
}
