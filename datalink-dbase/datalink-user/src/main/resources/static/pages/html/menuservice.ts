import request from 'umi-request';
import type { TableListParams } from './data.d';
import {MenuTableListItem} from "@/pages/Dbase/Menu/data";

export async function queryMenu(params?: TableListParams) {
    return request('/api-menu/menus/list', {
        method: 'POST',
        data: {
            ...params,
        },
    });
}

export async function removeMenu(params: number[]) {
    return request('/api-menu/menus', {
        method: 'DELETE',
        data: {
            ...params,
        },
    });
}

export async function addOrUpdateMenu(params: UserTableListItem) {
    return request('/api-menu/menus/saveOrUpdate', {
        method: 'POST',
        data: {
            ...params,
        },
    });
}

