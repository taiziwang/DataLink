package com.datalink.base.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * LbIsolationContextHolder
 *
 * @author wenmo
 * @since 2021/5/10 21:55
 */
public class LbIsolationContextHolder {
    private static final ThreadLocal<String> VERSION_CONTEXT = new TransmittableThreadLocal<>();

    public static void setVersion(String version) {
        VERSION_CONTEXT.set(version);
    }

    public static String getVersion() {
        return VERSION_CONTEXT.get();
    }

    public static void clear() {
        VERSION_CONTEXT.remove();
    }
}
