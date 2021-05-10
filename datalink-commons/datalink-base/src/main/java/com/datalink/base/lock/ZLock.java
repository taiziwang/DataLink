package com.datalink.base.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 锁
 *
 * @author wenmo
 * @since 2021/5/3 19:39
 */
@AllArgsConstructor
public class ZLock implements AutoCloseable {
    @Getter
    private final Object lock;

    private final DistributedLock locker;

    @Override
    public void close() throws Exception {
        locker.unlock(lock);
    }
}
