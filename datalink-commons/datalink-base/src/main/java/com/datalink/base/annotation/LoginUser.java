package com.datalink.base.annotation;

import java.lang.annotation.*;

/**
 * 当前登录人信息
 *
 * @author wenmo
 * @since 2021/5/3 21:17
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {
    /**
     * 是否查询SysUser对象所有信息，true则通过rpc接口查询
     */
    boolean isFull() default false;
}