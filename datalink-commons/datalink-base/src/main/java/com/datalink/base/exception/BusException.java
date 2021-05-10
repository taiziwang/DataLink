package com.datalink.base.exception;

/**
 * BusException
 *
 * @author wenmo
 * @since 2021/5/10 21:57
 */
public class BusException extends RuntimeException {
    private static final long serialVersionUID = 773925975528404646L;

    public BusException(String message) {
        super(message);
    }
}
