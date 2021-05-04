package com.datalink.base.annotation;

import java.lang.annotation.*;

/**
 * 注入当前登录账号的租户ID
 *
 * @author wenmo
 * @since 2021/5/3 21:18
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginClient {
}
