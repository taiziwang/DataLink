package com.datalink.db.plug.gainer;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.datalink.db.plug.config.*;
import com.datalink.db.plug.config.querys.H2Query;
import com.datalink.db.plug.config.rules.DateType;
import com.datalink.db.plug.entity.Column;
import com.datalink.db.plug.entity.Schema;
import com.datalink.db.plug.entity.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description 数据源信息获取者
 * @Author wenmo
 * @Date 2020/10/20
 **/
public class DbGainer {
    /**
     * 数据库配置
     */
    private final DataSourceConfig dataSourceConfig;
    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig = new GlobalConfig();
    /**
     * SQL连接
     */
    private Connection connection;
    /**
     * SQL语句类型
     */
    private IDbQuery dbQuery;
    private DbType dbType;
    /**
     * 是否支持注释
     */
    private boolean commentSupported;

    public DbGainer(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
        handlerDataSource(dataSourceConfig);
    }


    private void handlerDataSource(DataSourceConfig config) {
        connection = config.getConn();
        dbType = config.getDbType();
        dbQuery = config.getDbQuery();
        globalConfig.setDateType(DateType.ONLY_DATE);
        //SQLITE 数据库不支持注释获取
        commentSupported = !dataSourceConfig.getDbType().equals(DbType.SQLITE);
    }

    public List<Schema> getSchemas() {
        List<Schema> schemas = new ArrayList<>();
        String schemasSql = dbQuery.schemaAllSql();
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(schemasSql);
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String schemaName = results.getString(dbQuery.schemaName());
                    if (StringUtils.isNotBlank(schemaName)) {
                        Schema schema = new Schema(schemaName);
                        schemas.add(schema);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schemas;
    }

    public List<Schema> getSchemaAndTables() {
        List<Schema> schemas = getSchemas();
        try {
            String tablesSql = dbQuery.tableAllSql();
            try (PreparedStatement preparedStatement = connection.prepareStatement(tablesSql);
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String tableName = results.getString(dbQuery.tableName());
                    if (StringUtils.isNotBlank(tableName)) {
                        Table tableInfo = new Table(tableName);
                        if (commentSupported) {
                            String tableComment = results.getString(dbQuery.tableComment());
                            tableInfo.setComment(tableComment);
                        }
                        String schemaName = results.getString(dbQuery.schemaName());
                        tableInfo.setSchema(schemaName);
                        for (int i = 0; i < schemas.size(); i++) {
                            if (schemaName != null && schemaName.equals(schemas.get(i).getName())) {
                                schemas.get(i).getTables().add(tableInfo);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schemas;
    }

    public List<Table> getTables() {
        List<Table> tableList = new ArrayList<>();
        String schema = dataSourceConfig.getSchemaName();
        try {
            String tablesSql = dbQuery.tablesSql();
            if (DbType.POSTGRE_SQL == this.dbType) {
                if (schema == null) {
                    //pg 默认 schema=public
                    schema = "public";
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            } else if (DbType.KINGBASE_ES == this.dbType) {
                if (schema == null) {
                    //kingbase 默认 schema=PUBLIC
                    schema = "PUBLIC";
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            } else if (DbType.DB2 == this.dbType) {
                if (schema == null) {
                    //db2 默认 schema=current schema
                    schema = "current schema";
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            }
            //oracle数据库表太多，出现最大游标错误
            else if (DbType.ORACLE == this.dbType) {
                //oracle 默认 schema=username
                if (schema == null) {
                    schema = dataSourceConfig.getUsername().toUpperCase();
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            }
            StringBuilder sql = new StringBuilder(tablesSql);

            Table tableInfo = new Table();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String tableName = results.getString(dbQuery.tableName());
                    if (StringUtils.isNotBlank(tableName)) {
                        tableInfo.setName(tableName);
                        if (commentSupported) {
                            String tableComment = results.getString(dbQuery.tableComment());
                            tableInfo.setComment(tableComment);
                        }
                        tableInfo.setSchema(schema);
                        tableList.add(tableInfo);
                    }
                }
            }
           /* for (int i = 0; i < tableList.size(); i++) {
                tableList.get(i).setColumns(getColumns(tableList.get(i).getName()));
            }*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

    public List<Table> getTablesAndColumns() {
        List<Table> tableList = new ArrayList<>();
        tableList = getTables();
        for (int i = 0; i < tableList.size(); i++) {
            tableList.get(i).setColumns(getColumns(tableList.get(i).getName()));
        }
        return tableList;
    }

    /**
     * 将字段信息与表信息关联
     *
     * @param tableName 表信息
     * @return ignore
     */
    public List<Column> getColumns(String tableName) {
        boolean haveId = false;
        List<Column> columns = new ArrayList<>();
        DbType dbType = this.dbType;
        try {
            String tableFieldsSql = dbQuery.tableFieldsSql();
            Set<String> h2PkColumns = new HashSet<>();
            if (DbType.POSTGRE_SQL == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.KINGBASE_ES == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.DB2 == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.ORACLE == dbType) {
                tableName = tableName.toUpperCase();
                tableFieldsSql = String.format(tableFieldsSql.replace("#schema", dataSourceConfig.getSchemaName()), tableName);
            } else if (DbType.DM == dbType) {
                tableName = tableName.toUpperCase();
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            } else if (DbType.H2 == dbType) {
                try (PreparedStatement pkQueryStmt = connection.prepareStatement(String.format(H2Query.PK_QUERY_SQL, tableName));
                     ResultSet pkResults = pkQueryStmt.executeQuery()) {
                    while (pkResults.next()) {
                        String primaryKey = pkResults.getString(dbQuery.fieldKey());
                        if (Boolean.parseBoolean(primaryKey)) {
                            h2PkColumns.add(pkResults.getString(dbQuery.fieldName()));
                        }
                    }
                }
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            } else {
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            }
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(tableFieldsSql);
                    ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    Column field = new Column();
                    String columnName = results.getString(dbQuery.fieldName());
                    // 避免多重主键设置，目前只取第一个找到ID，并放到list中的索引为0的位置
                    boolean isId;
                    if (DbType.H2 == dbType) {
                        isId = h2PkColumns.contains(columnName);
                    } else {
                        String key = results.getString(dbQuery.fieldKey());
                        if (DbType.DB2 == dbType || DbType.SQLITE == dbType) {
                            isId = StringUtils.isNotBlank(key) && "1".equals(key);
                        } else {
                            isId = StringUtils.isNotBlank(key) && "PRI".equals(key.toUpperCase());
                        }
                    }

                    // 处理ID
                    /*if (isId && !haveId) {
                        field.setKeyFlag(true);
                        if (DbType.H2 == dbType || DbType.SQLITE == dbType || dbQuery.isKeyIdentity(results)) {
                            field.setKeyIdentityFlag(true);
                        }
                        haveId = true;
                    } else {
                        field.setKeyFlag(false);
                    }*/
                    if (isId) {
                        field.setKeyFlag(true);
                        if (DbType.H2 == dbType || DbType.SQLITE == dbType || dbQuery.isKeyIdentity(results)) {
                            field.setKeyIdentityFlag(true);
                        }
                    } else {
                        field.setKeyFlag(false);
                    }
                    // 处理其它信息
                    field.setName(columnName);
                    String newColumnName = columnName;
                    IKeyWordsHandler keyWordsHandler = dataSourceConfig.getKeyWordsHandler();
                    if (keyWordsHandler != null) {
                        if (keyWordsHandler.isKeyWords(columnName)) {
                            System.err.println(String.format("当前表[%s]存在字段[%s]为数据库关键字或保留字!", tableName, columnName));
                            field.setKeyWords(true);
                            newColumnName = keyWordsHandler.formatColumn(columnName);
                        }
                    }
                    field.setColumnName(newColumnName);
                    field.setType(results.getString(dbQuery.fieldType()));
                    field.setColumnType(dataSourceConfig.getTypeConvert().processTypeConvert(globalConfig, field.getType()).getType());
                    if (commentSupported) {
                        field.setComment(results.getString(dbQuery.fieldComment()));
                    }
                    field.setIsNotNull(results.getString(dbQuery.isNotNull()));

                    columns.add(field);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception：" + e.getMessage());
        }
        return columns;
    }
}
