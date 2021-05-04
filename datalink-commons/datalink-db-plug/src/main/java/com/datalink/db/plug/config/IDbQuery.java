package com.datalink.db.plug.config;

import com.baomidou.mybatisplus.annotation.DbType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 表数据查询接口
 */
public interface IDbQuery {

    /**
     * 数据库类型
     *
     * @deprecated 3.3.1 {@link DataSourceConfig#setDbType(DbType)}
     */
    @Deprecated
    default DbType dbType() {
        return null;
    }


    /**
     * 所有数据库信息查询 SQL
     */
    String schemaAllSql();
    /**
     * 所有表信息查询 SQL
     */
    String tableAllSql();

    /**
     * 表信息查询 SQL
     */
    String tablesSql();


    /**
     * 表字段信息查询 SQL
     */
    String tableFieldsSql();


    /**
     * 表名称
     */
    String schemaName();

    /**
     * 表名称
     */
    String tableName();


    /**
     * 表注释
     */
    String tableComment();


    /**
     * 字段名称
     */
    String fieldName();


    /**
     * 字段类型
     */
    String fieldType();


    /**
     * 字段注释
     */
    String fieldComment();


    /**
     * 主键字段
     */
    String fieldKey();


    /**
     * 判断主键是否为identity，目前仅对mysql进行检查
     *
     * @param results ResultSet
     * @return 主键是否为identity
     * @throws SQLException ignore
     */
    boolean isKeyIdentity(ResultSet results) throws SQLException;

    /**
     * 判断字段是否不为null，目前仅对mysql进行检查
     *
     * @return 主键是否不为bull
     */
    String isNotNull();

    /**
     * 自定义字段名称
     */
    String[] fieldCustom();
}
