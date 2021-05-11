package com.datalink.uaa.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.datalink.db.mybatis.mapper.SuperMapper;
import com.datalink.uaa.entity.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClientMapper
 *
 * @author wenmo
 * @since 2021/5/11
 */
@Mapper
public interface ClientMapper extends SuperMapper<Client> {
    List<Client> findList(Page<Client> page, @Param("params") Map<String, Object> params);
}
