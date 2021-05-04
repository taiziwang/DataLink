package com.datalink.db.plug.config;

public enum CustomDbType {

    CLICKHOUSE("clickhouse", "ClickHouse数据库","com.baomidou.mybatisplus.extension.plugins.pagination.dialects.ClickHouseDialect"),
    MYSQL("mysql", "MySql数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.MySqlDialect"),
    MARIADB("mariadb", "MariaDB数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.MariaDBDialect"),
    ORACLE("oracle", "Oracle11g及以下数据库(高版本推荐使用ORACLE_NEW)", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.OracleDialect"),
    ORACLE_12C("oracle12c", "Oracle12c+数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.Oracle12cDialect"),
    DB2("db2", "DB2数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.DB2Dialect"),
    H2("h2", "H2数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.H2Dialect"),
    HSQL("hsql", "HSQL数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.HSQLDialect"),
    SQLITE("sqlite", "SQLite数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.SQLiteDialect"),
    POSTGRE_SQL("postgresql", "Postgre数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.PostgreDialect"),
    SQL_SERVER2005("sqlserver2005", "SQLServer2005数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.SQLServer2005Dialect"),
    SQL_SERVER("sqlserver", "SQLServer数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.SQLServerDialect"),
    DM("dm", "达梦数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.DmDialect"),
    XU_GU("xugu", "虚谷数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.XuGuDialect"),
    KINGBASE_ES("kingbasees", "人大金仓数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.KingbaseDialect"),
    PHOENIX("phoenix", "Phoenix HBase数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.PhoenixDialect"),
    GAUSS("zenith", "Gauss 数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.GaussDialect"),
    OTHER("other", "其他数据库", "com.baomidou.mybatisplus.extension.plugins.pagination.dialects.UnknownDialect");

    private final String db;
    private final String desc;
    /** @deprecated */
    @Deprecated
    private String dialect;

    public static CustomDbType getCustomDbType(String dbType) {
        CustomDbType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CustomDbType type = var1[var3];
            if (type.db.equalsIgnoreCase(dbType)) {
                return type;
            }
        }

        return OTHER;
    }

    public String getDb() {
        return this.db;
    }

    public String getDesc() {
        return this.desc;
    }

    /** @deprecated */
    @Deprecated
    public String getDialect() {
        return this.dialect;
    }

    private CustomDbType(final String db, final String desc, final String dialect) {
        this.db = db;
        this.desc = desc;
        this.dialect = dialect;
    }
}
