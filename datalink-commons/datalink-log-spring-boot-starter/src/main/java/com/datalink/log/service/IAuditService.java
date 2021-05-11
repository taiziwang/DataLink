package com.datalink.log.service;

import com.datalink.log.model.Audit;

/**
 * IAuditService
 *
 * @author wenmo
 * @since 2021/5/11
 */
public interface IAuditService {
    void save(Audit audit);
}
