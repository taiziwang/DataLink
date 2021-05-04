package com.datalink.db.mybatis.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @Description 租户Context
 * @Author wenmo
 * @Date 2021/5/3 14:21
 */
public class TenantContext {

    private static final ThreadLocal<String> CONTEXT = new TransmittableThreadLocal<>();

    public static void setTenant(String tenant) {
        CONTEXT.set(tenant);
    }

    public static String getTenant() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
