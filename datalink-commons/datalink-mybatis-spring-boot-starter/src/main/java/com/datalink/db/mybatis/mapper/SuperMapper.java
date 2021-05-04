package com.datalink.db.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description MybatisPlus Mapper 的父类
 * @Author wenmo
 * @Date 2021/5/3 13:22
 */
public interface SuperMapper<T> extends BaseMapper<T> {

    List<T> selectForCTable(Page<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

}
