package com.datalink.db.plug.config.querys;

/**
 * Sqlite 表数据查询
 */
public class SqliteQuery extends AbstractDbQuery {

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
        return "select * from sqlite_master where type='table'";
    }


    @Override
    public String tableFieldsSql() {
        return "pragma table_info('%s');";
    }

    @Override
    public String schemaName() {
        return null;
    }


    @Override
    public String tableName() {
        return "name";
    }


    @Override
    public String tableComment() {
        return "";
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
        return "";
    }


    @Override
    public String fieldKey() {
        return "pk";
    }

    @Override
    public String isNotNull() {
        return null;
    }
}
