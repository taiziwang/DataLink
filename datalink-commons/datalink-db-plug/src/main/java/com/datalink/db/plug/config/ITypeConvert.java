package com.datalink.db.plug.config;

import com.datalink.db.plug.config.po.TableField;
import com.datalink.db.plug.config.rules.IColumnType;

/**
 * 数据库字段类型转换
 */
public interface ITypeConvert {


    /**
     * 执行类型转换
     *
     * @param globalConfig 全局配置
     * @param tableField   字段列信息
     * @return ignore
     */
    default IColumnType processTypeConvert(GlobalConfig globalConfig, TableField tableField) {
        // 该方法提供重写
        return processTypeConvert(globalConfig, tableField.getType());
    }


    /**
     * 执行类型转换
     *
     * @param globalConfig 全局配置
     * @param fieldType    字段类型
     * @return ignore
     */
    IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType);
}
