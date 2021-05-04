package com.datalink.db.plug.config.querys;

/**
 * DB2 表数据查询
 */
public class DB2Query extends AbstractDbQuery {

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
        return "SELECT * FROM SYSCAT.TABLES where tabschema=%s";
    }


    @Override
    public String tableFieldsSql() {
        return "SELECT * FROM syscat.columns WHERE tabschema=%s AND tabname='%s'";
    }

    @Override
    public String schemaName() {
        return null;
    }


    @Override
    public String tableName() {
        return "TABNAME";
    }


    @Override
    public String tableComment() {
        return "REMARKS";
    }


    @Override
    public String fieldName() {
        return "COLNAME";
    }


    @Override
    public String fieldType() {
        return "TYPENAME";
    }


    @Override
    public String fieldComment() {
        return "REMARKS";
    }


    @Override
    public String fieldKey() {
        return "IDENTITY";
    }

    @Override
    public String isNotNull() {
        return null;
    }

}
