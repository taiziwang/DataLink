package com.datalink.uaa.dao;

import com.datalink.uaa.entity.Tenant;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租户信息 Mapper 接口
 * @author wenmo
 * @since 2021-05-10
 */
@Mapper
public interface TenantMapper extends SuperMapper<Tenant> {

}
