package com.datalink.db.plug.config.converts;

import com.datalink.db.plug.config.GlobalConfig;
import com.datalink.db.plug.config.ITypeConvert;
import com.datalink.db.plug.config.rules.DbColumnType;
import com.datalink.db.plug.config.rules.IColumnType;

/**
 * ORACLE 字段类型转换
 */
public class OracleTypeConvert implements ITypeConvert {

    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("char")) {
            return DbColumnType.STRING;
        } else if (t.contains("date") || t.contains("timestamp")) {
            switch (globalConfig.getDateType()) {
                case ONLY_DATE:
                    return DbColumnType.DATE;
                case SQL_PACK:
                    return DbColumnType.TIMESTAMP;
                case TIME_PACK:
                    return DbColumnType.LOCAL_DATE_TIME;
            }
        } else if (t.contains("number")) {
            if (t.matches("number\\(+\\d\\)")) {
                return DbColumnType.INTEGER;
            } else if (t.matches("number\\(+\\d{2}+\\)")) {
                return DbColumnType.LONG;
            }
            return DbColumnType.BIG_DECIMAL;
        } else if (t.contains("float")) {
            return DbColumnType.FLOAT;
        } else if (t.contains("clob")) {
            return DbColumnType.STRING;
        } else if (t.contains("blob")) {
            return DbColumnType.BLOB;
        } else if (t.contains("binary")) {
            return DbColumnType.BYTE_ARRAY;
        } else if (t.contains("raw")) {
            return DbColumnType.BYTE_ARRAY;
        }
        return DbColumnType.STRING;
    }

}
