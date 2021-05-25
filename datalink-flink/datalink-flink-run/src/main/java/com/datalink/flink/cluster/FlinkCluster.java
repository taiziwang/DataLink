package com.datalink.flink.cluster;

import cn.hutool.http.HttpUtil;
import com.datalink.base.redis.template.RedisRepository;
import com.datalink.flink.constant.FlinkConstant;
import com.datalink.flink.constant.FlinkHistoryConstant;
import com.datalink.flink.constant.NetConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * FlinkCluster
 *
 * @author qiwenkai
 * @since 2021/5/25 15:08
 **/
public class FlinkCluster {

    public static String getFlinkJobManagerIP(String flinkServers, RedisRepository redisRepository) {
        Map<String, String> map = new HashMap();
        String res = "";
        String flinkAddress = "";
        try {
            flinkAddress = redisRepository.get(FlinkConstant.FLINK_JOB_MANAGER_HOST).toString();
            res = HttpUtil.get(NetConstant.HTTP + flinkAddress + NetConstant.COLON + NetConstant.PORT +  NetConstant.SLASH + FlinkHistoryConstant.JOBS, NetConstant.SERVER_TIME_OUT_ACTIVE);
            if (!res.isEmpty()) {
                return flinkAddress;
            }
        } catch (Exception e) {
        }
        String[] servers = flinkServers.split(",");
        for (String server : servers) {
            try {
                String url = NetConstant.HTTP + server + NetConstant.COLON + NetConstant.PORT +  NetConstant.SLASH + FlinkHistoryConstant.JOBS;
                res = HttpUtil.get(url, NetConstant.SERVER_TIME_OUT_ACTIVE);
                if (!res.isEmpty()) {
                    map.put(FlinkConstant.FLINK_JOB_MANAGER_HOST, server);
                    if(server.equalsIgnoreCase(flinkAddress)){
                        redisRepository.set(FlinkConstant.FLINK_JOB_MANAGER_HOST,server);
                    }
                    return server;
                }
            } catch (Exception e) {
            }
        }
        return "";
    }
}
