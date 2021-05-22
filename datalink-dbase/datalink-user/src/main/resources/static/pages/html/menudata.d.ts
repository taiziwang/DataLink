export type MenuTableListItem = {
            id: props.values.id,
            parentId: props.values.parentId,
            name: props.values.name,
            url: props.values.url,
            path: props.values.path,
            pathMethod: props.values.pathMethod,
            css: props.values.css,
            sort: props.values.sort,
            createTime: props.values.createTime,
            updateTime: props.values.updateTime,
            type: props.values.type,
            enabled: props.values.enabled,
            tenantId: props.values.tenantId,
};

export type TableListPagination = {
    total: number;
    pageSize: number;
    current: number;
};

export type TableListData = {
    list: TableListItem[];
    pagination: Partial<TableListPagination>;
};

export type TableListParams = {
    status?: string;
    name?: string;
    desc?: string;
    key?: number;
    pageSize?: number;
    currentPage?: number;
    filter?: Record<string, any[]>;
    sorter?: Record<string, any>;
};
