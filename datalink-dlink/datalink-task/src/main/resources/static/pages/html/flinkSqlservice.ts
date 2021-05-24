import request from 'umi-request';
import type { TableListParams } from './data.d';
import {FlinkSqlTableListItem} from "@/pages/Dbase/FlinkSql/data";

export async function queryFlinkSql(params?: TableListParams) {
    return request('/api-flinkSql/flinkSqls/list', {
        method: 'POST',
        data: {
            ...params,
        },
    });
}

export async function removeFlinkSql(params: number[]) {
    return request('/api-flinkSql/flinkSqls', {
        method: 'DELETE',
        data: {
            ...params,
        },
    });
}

export async function addOrUpdateFlinkSql(params: UserTableListItem) {
    return request('/api-flinkSql/flinkSqls/saveOrUpdate', {
        method: 'POST',
        data: {
            ...params,
        },
    });
}

