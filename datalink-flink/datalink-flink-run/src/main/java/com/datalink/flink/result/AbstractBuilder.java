package com.datalink.flink.result;

import org.apache.flink.table.api.TableResult;

import java.util.List;
import java.util.Map;

/**
 * AbstractBuilder
 *
 * @author qiwenkai
 * @since 2021/5/25 16:11
 **/
public class AbstractBuilder {

    protected String operationType;
    protected Integer maxRowNum;
    protected boolean printRowKind;
    protected String nullColumn;

    /*public static ResultBuilder build(String operationType, Integer maxRowNum,String nullColumn,boolean printRowKind) {
        switch (operationType.toUpperCase()){
            case SelectBuilder.OPERATION_TYPE:
                return new SelectBuilder(operationType,maxRowNum,nullColumn,printRowKind);
            default:
                return new SelectBuilder(operationType,maxRowNum,nullColumn,printRowKind);
        }
    }*/
}
