package com.datalink.db.plug.config.querys;

import com.baomidou.mybatisplus.annotation.DbType;
import com.datalink.db.plug.config.IDbQuery;

import java.util.EnumMap;
import java.util.Map;

/**
 * DB查询注册
 */
public class DbQueryRegistry {

    private final Map<DbType, IDbQuery> db_query_enum_map = new EnumMap<>(DbType.class);

    public DbQueryRegistry() {
        db_query_enum_map.put(DbType.ORACLE, new OracleQuery());
        db_query_enum_map.put(DbType.SQL_SERVER, new SqlServerQuery());
        db_query_enum_map.put(DbType.POSTGRE_SQL, new PostgreSqlQuery());
        db_query_enum_map.put(DbType.DB2, new DB2Query());
        db_query_enum_map.put(DbType.MARIADB, new MariadbQuery());
        db_query_enum_map.put(DbType.H2, new H2Query());
        db_query_enum_map.put(DbType.SQLITE, new SqliteQuery());
        db_query_enum_map.put(DbType.DM, new DMQuery());
        db_query_enum_map.put(DbType.KINGBASE_ES, new KingbaseESQuery());
        db_query_enum_map.put(DbType.MYSQL, new MySqlQuery());
        db_query_enum_map.put(DbType.GAUSS, new GaussQuery());
    }

    public IDbQuery getDbQuery(DbType dbType) {
        return db_query_enum_map.get(dbType);
    }
}
