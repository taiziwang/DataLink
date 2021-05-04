package com.datalink.db.plug.config.converts;

import com.baomidou.mybatisplus.annotation.DbType;
import com.datalink.db.plug.config.ITypeConvert;

import java.util.EnumMap;
import java.util.Map;

/**
 * 类型转换注册
 */
public class TypeConvertRegistry {

    private final Map<DbType, ITypeConvert> type_convert_enum_map = new EnumMap<>(DbType.class);

    public TypeConvertRegistry() {
        type_convert_enum_map.put(DbType.ORACLE, new OracleTypeConvert());
        type_convert_enum_map.put(DbType.SQL_SERVER, new SqlServerTypeConvert());
        type_convert_enum_map.put(DbType.POSTGRE_SQL, new PostgreSqlTypeConvert());
        type_convert_enum_map.put(DbType.DB2, new DB2TypeConvert());
        type_convert_enum_map.put(DbType.SQLITE, new SqliteTypeConvert());
        type_convert_enum_map.put(DbType.DM, new DmTypeConvert());
        type_convert_enum_map.put(DbType.MARIADB, new MySqlTypeConvert());
        type_convert_enum_map.put(DbType.KINGBASE_ES, new KingbaseESTypeConvert());
        type_convert_enum_map.put(DbType.MYSQL, new MySqlTypeConvert());
        type_convert_enum_map.put(DbType.GAUSS, new GaussTypeConvert());
    }

    public ITypeConvert getTypeConvert(DbType dbType) {
        return type_convert_enum_map.get(dbType);
    }
}
