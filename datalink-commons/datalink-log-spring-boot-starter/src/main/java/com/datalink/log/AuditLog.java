package com.datalink.log;

import java.lang.annotation.*;

/**
 * AuditLog
 *
 * @author wenmo
 * @since 2021/5/11
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    /**
     * 操作信息
     */
    String operation();
}
