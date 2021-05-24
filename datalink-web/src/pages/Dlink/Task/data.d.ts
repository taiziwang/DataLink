export type TaskTableListItem = {
            id: props.values.id,
            name: props.values.name,
            alias: props.values.alias,
            type: props.values.type,
            note: props.values.note,
            enabled: props.values.enabled,
            createUser: props.values.createUser,
            createTime: props.values.createTime,
            updateUser: props.values.updateUser,
            updateTime: props.values.updateTime,
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
