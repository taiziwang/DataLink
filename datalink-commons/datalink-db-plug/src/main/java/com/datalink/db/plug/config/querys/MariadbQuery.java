package com.datalink.db.plug.config.querys;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySql 表数据查询
 */
public class MariadbQuery extends AbstractDbQuery {

    @Override
    public String schemaAllSql() {
        return null;
    }

    @Override
    public String tableAllSql() {
        return null;
    }

    @Override
    public String tablesSql() {
        return "show table status WHERE 1=1 ";
    }


    @Override
    public String tableFieldsSql() {
        return "show full fields from `%s`";
    }

    @Override
    public String schemaName() {
        return null;
    }


    @Override
    public String tableName() {
        return "NAME";
    }


    @Override
    public String tableComment() {
        return "COMMENT";
    }


    @Override
    public String fieldName() {
        return "FIELD";
    }


    @Override
    public String fieldType() {
        return "TYPE";
    }


    @Override
    public String fieldComment() {
        return "COMMENT";
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
        return null;
    }
}
