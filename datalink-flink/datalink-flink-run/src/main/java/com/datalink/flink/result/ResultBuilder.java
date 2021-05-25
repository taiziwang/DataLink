package com.datalink.flink.result;

import org.apache.flink.table.api.TableResult;

import java.util.List;
import java.util.Map;

/**
 * ResultBuilder
 *
 * @author qiwenkai
 * @since 2021/5/25 15:59
 **/
public interface ResultBuilder {

    static ResultBuilder build(String operationType,Integer maxRowNum,String nullColumn,boolean printRowKind){
        switch (operationType.toUpperCase()){
            case SelectBuilder.OPERATION_TYPE:
                return new SelectBuilder(operationType,maxRowNum,nullColumn,printRowKind);
            default:
                return new SelectBuilder(operationType,maxRowNum,nullColumn,printRowKind);
        }
    }

    IResult getResult(TableResult tableResult);
}
