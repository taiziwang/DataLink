package com.dlink.constant;

/**
 * FlinkConstant
 *
 * @author wenmo
 * @since 2021/5/25 14:39
 **/
public interface FlinkConstant {

    /**
     * flink端口
     */
    Integer PORT = 8081;
    /**
     * flink会话默认个数
     */
    Integer DEFAULT_SESSION_COUNT = 256;
    /**
     * flink加载因子
     */
    Double DEFAULT_FACTOR = 0.75;
    /**
     * flink运行节点
     */
    String FLINK_JOB_MANAGER_HOST = "flinkJobManagerHOST";
    /**
     * 本地模式host
     */
    String LOCAL_HOST = "localhost:8081";
}
