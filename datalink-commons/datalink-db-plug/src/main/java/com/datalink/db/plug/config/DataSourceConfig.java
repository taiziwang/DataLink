package com.datalink.db.plug.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.datalink.db.plug.config.converts.TypeConvertRegistry;
import com.datalink.db.plug.config.querys.DbQueryRegistry;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 * 数据库配置
 */
@Data
@Accessors(chain = true)
public class DataSourceConfig {

    /**
     * 数据库信息查询
     */
    private IDbQuery dbQuery;
    /**
     * 数据库类型
     */
    private DbType dbType;
    /**
     * PostgreSQL schemaName
     */
    private String schemaName;
    /**
     * 类型转换
     */
    private ITypeConvert typeConvert;
    /**
     * 关键字处理器
     * @since 3.3.2
     */
    private IKeyWordsHandler keyWordsHandler;
    /**
     * 驱动连接的URL
     */
    private String url;
    /**
     * 驱动名称
     */
    private String driverName;
    /**
     * 数据库连接用户名
     */
    private String username;
    /**
     * 数据库连接密码
     */
    private String password;

    public IDbQuery getDbQuery() {
        if (null == dbQuery) {
            DbType dbType = getDbType();
            DbQueryRegistry dbQueryRegistry = new DbQueryRegistry();
            // 默认 MYSQL
            dbQuery = Optional.ofNullable(dbQueryRegistry.getDbQuery(dbType))
                    .orElseGet(() -> dbQueryRegistry.getDbQuery(DbType.MYSQL));
        }
        return dbQuery;
    }

    /**
     * 判断数据库类型
     *
     * @return 类型枚举值
     */
    public DbType getDbType() {
        if (null == this.dbType) {
            this.dbType = this.getDbType(this.driverName);
            if (null == this.dbType) {
                this.dbType = this.getDbType(this.url.toLowerCase());
                if (null == this.dbType) {
                    throw ExceptionUtils.mpe("Unknown type of database!");
                }
            }
        }

        return this.dbType;
    }

    /**
     * 判断数据库类型
     *
     * @param str 用于寻找特征的字符串，可以是 driverName 或小写后的 url
     * @return 类型枚举值，如果没找到，则返回 null
     */
    private DbType getDbType(String str) {
        if (str.contains("mysql")) {
            return DbType.MYSQL;
        } else if (str.contains("oracle")) {
            return DbType.ORACLE;
        } else if (str.contains("postgresql")) {
            return DbType.POSTGRE_SQL;
        } else if (str.contains("sqlserver")) {
            return DbType.SQL_SERVER;
        } else if (str.contains("db2")) {
            return DbType.DB2;
        } else if (str.contains("mariadb")) {
            return DbType.MARIADB;
        } else if (str.contains("sqlite")) {
            return DbType.SQLITE;
        } else if (str.contains("h2")) {
            return DbType.H2;
        } else if (str.contains("kingbase") || str.contains("kingbase8")) {
            return DbType.KINGBASE_ES;
        } else if (str.contains("dm")) {
            return DbType.DM;
        } else if (str.contains("zenith")) {
            return DbType.GAUSS;
        } else {
            return DbType.OTHER;
        }
    }

    public ITypeConvert getTypeConvert() {
        if (null == typeConvert) {
            DbType dbType = getDbType();
            TypeConvertRegistry typeConvertRegistry = new TypeConvertRegistry();
            // 默认 MYSQL
            typeConvert = Optional.ofNullable(typeConvertRegistry.getTypeConvert(dbType)).orElseGet(() -> typeConvertRegistry.getTypeConvert(DbType.MYSQL));
        }
        return typeConvert;
    }

    /**
     * 创建数据库连接对象
     *
     * @return Connection
     */
    public Connection getConn() {
        Connection conn;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
