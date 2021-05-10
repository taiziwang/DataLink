package com.datalink.base.exception;

/**
 * 分布式锁异常
 *
 * @author wenmo
 * @since 2021/5/3 19:45
 */
public class LockException extends RuntimeException {

    private static final long serialVersionUID = -6177093429496568602L;

    public LockException(String message) {
        super(message);
    }
}
