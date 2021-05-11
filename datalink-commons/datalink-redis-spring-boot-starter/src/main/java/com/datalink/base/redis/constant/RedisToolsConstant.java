package com.datalink.base.redis.constant;

/**
 * RedisToolsConstant
 *
 * @author wenmo
 * @since 2021/5/11
 */
public class RedisToolsConstant {
    private RedisToolsConstant() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * single Redis
     */
    public final static int SINGLE = 1 ;

    /**
     * Redis cluster
     */
    public final static int CLUSTER = 2 ;
}
