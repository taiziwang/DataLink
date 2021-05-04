package com.datalink.db.plug.config.querys;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clichouse 表数据查询
 */
public class ClickHouseQuery extends AbstractDbQuery{
    @Override
    public String schemaAllSql() {
        return "show databases";
    }

    @Override
    public String tableAllSql() {
        return "show tables";
    }

    @Override
    public String tablesSql() {
        return "show table status WHERE 1=1 ";
    }


    @Override
    public String tableFieldsSql() {
        return "desc `%s`";
    }

    @Override
    public String schemaName() {
        return "db";
    }


    @Override
    public String tableName() {
        return "name";
    }


    @Override
    public String tableComment() {
        return "comment";
    }


    @Override
    public String fieldName() {
        return "name";
    }


    @Override
    public String fieldType() {
        return "type";
    }


    @Override
    public String fieldComment() {
        return "comment";
    }


    @Override
    public String fieldKey() {
        return "KEY";
    }


    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return "auto_increment".equals(results.getString("Extra"));
    }

    @Override
    public String isNotNull() {
        return "NULL";
    }
}
