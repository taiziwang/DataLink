package com.datalink.user.dao;

import com.datalink.user.entity.Dict;
import com.datalink.db.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 扩展字典 Mapper 接口
 * @author wenmo
 * @since 2021-05-09
 */
@Mapper
public interface DictMapper extends SuperMapper<Dict> {

}
