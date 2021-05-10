package com.datalink.base.exception;

/**
 * 幂等性异常
 *
 * @author wenmo
 * @since 2021/5/3 19:49
 */
public class IdempotencyException extends RuntimeException {

    private static final long serialVersionUID = -71707696609095323L;

    public IdempotencyException(String message) {
        super(message);
    }
}
