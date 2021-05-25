import {DownOutlined, PlusOutlined, UserOutlined} from '@ant-design/icons';
import {Button, message, Input, Drawer, Modal} from 'antd';
import React, {useState, useRef} from 'react';
import {PageContainer, FooterToolbar} from '@ant-design/pro-layout';
import type {ProColumns, ActionType} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import ProDescriptions from '@ant-design/pro-descriptions';
import CreateForm from './components/CreateForm';
import UpdateForm from './components/UpdateForm';
import type {TaskTableListItem} from './data.d';
import {queryTask, addOrUpdateTask, removeTask, submitTask} from './service';

import styles from './index.less';

import Dropdown from "antd/es/dropdown/dropdown";
import Menu from "antd/es/menu";


const handleAddOrUpdate = async (fields: TaskTableListItem) => {
  const tipsTitle = fields.id ? "修改" : "添加";
  const hide = message.loading(`正在${tipsTitle}`);
  try {
    const {msg} = await addOrUpdateTask({...fields});
    hide();
    message.success(msg);
    return true;
  } catch (error) {
    hide();
    message.error(error);
    return false;
  }
};

const handleRemove = async (selectedRows: TaskTableListItem[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    const {msg} = await removeTask(selectedRows.map((row) => row.id));
    hide();
    message.success(msg);
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试');
    return false;
  }
};

const handleSubmit = async (selectedRows: TaskTableListItem[]) => {
  const hide = message.loading('正在执行');
  if (!selectedRows) return true;
  try {
    const {msg} = await submitTask(selectedRows.map((row) => row.id));
    hide();
    message.success(msg);
    return true;
  } catch (error) {
    hide();
    message.error('执行失败，请重试');
    return false;
  }
};

const TaskTableList: React.FC<{}> = () => {
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const [formValues, setFormValues] = useState({});
  const actionRef = useRef<ActionType>();
  const [row, setRow] = useState<TaskTableListItem>();
  const [selectedRowsState, setSelectedRows] = useState<TaskTableListItem[]>([]);

  const editAndDelete = (key: string | number, currentItem: TaskTableListItem) => {
    if (key === 'edit') {
      handleUpdateModalVisible(true);
      setFormValues(currentItem);
    } else if (key === 'delete') {
      Modal.confirm({
        title: '删除作业',
        content: '确定删除该作业吗？',
        okText: '确认',
        cancelText: '取消',
        onOk:async () => {
          await handleRemove([currentItem]);
          actionRef.current?.reloadAndRest?.();
        }
      });
    } else if (key === 'submit') {
      Modal.confirm({
        title: '执行作业',
        content: '确定执行该作业吗？',
        okText: '确认',
        cancelText: '取消',
        onOk:async () => {
          await handleSubmit([currentItem]);
          actionRef.current?.reloadAndRest?.();
        }
      });
    }
  };

  const updateEnabled = (selectedRows: TaskTableListItem[], enabled: boolean) => {
    selectedRows.forEach((item) => {
      handleAddOrUpdate({id: item.id, enabled: enabled})
    })
  };

  const MoreBtn: React.FC<{
    item: TaskTableListItem;
  }> = ({item}) => (
    <Dropdown
      overlay={
        <Menu onClick={({key}) => editAndDelete(key, item)}>
          <Menu.Item key="submit">执行</Menu.Item>
          <Menu.Item key="edit">编辑</Menu.Item>
          <Menu.Item key="delete">删除</Menu.Item>
        </Menu>
      }
    >
      <a>
        更多 <DownOutlined/>
      </a>
    </Dropdown>
  );

  const columns: ProColumns<TaskTableListItem>[] = [
    {
      title: '名称',
      dataIndex: 'name',
      tip: '名称是唯一的',
      sorter: true,
      formItemProps: {
        rules: [
          {
            required: true,
            message: '名称为必填项',
          },
        ],
      },
      render: (dom, entity) => {
        return <a onClick={() => setRow(entity)}>{dom}</a>;
      },
    },
    {
      title: '别名',
      sorter: true,
      dataIndex: 'alias',
      hideInForm: false,
      hideInSearch: true,
      hideInTable: false,
    },
    {
      title: '类型',
      sorter: true,
      dataIndex: 'type',
      hideInForm: false,
      hideInSearch: true,
      hideInTable: false,
    },
    {
      title: '注释',
      sorter: true,
      dataIndex: 'note',
      hideInForm: false,
      hideInSearch: true,
      hideInTable: false,
    },
    {
      title: '是否启用',
      sorter: true,
      dataIndex: 'enabled',
      hideInForm: true,
      hideInSearch: true,
      hideInTable: false,
      filters: [
        {
          text: '正常',
          value: 1,
        },
        {
          text: '禁用',
          value: 0,
        },
      ],
      filterMultiple: false,
      valueEnum: {
        true: { text: '正常', status: 'Success' },
        false: { text: '禁用', status: 'Error' },
      },
    },
    {
      title: '创建人',
      sorter: true,
      dataIndex: 'createUser',
      hideInForm: true,
      hideInSearch: true,
      hideInTable: false,
    },
    {
      title: '更新人',
      sorter: true,
      dataIndex: 'updateUser',
      hideInForm: true,
      hideInSearch: true,
      hideInTable: false,
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <a
          onClick={() => {
            handleUpdateModalVisible(true);
            setFormValues(record);
          }}
        >
          配置
        </a>,
        <MoreBtn key="more" item={record}/>,
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<TaskTableListItem>
        headerTitle="作业管理"
        actionRef={actionRef}
        rowKey="id"
        search={{
        labelWidth: 120,
      }}
        toolBarRender={() => [
        <Button type="primary" onClick={() => handleModalVisible(true)}>
          <PlusOutlined/> 新建
        </Button>,
      ]}
        request={(params, sorter, filter) => queryTask({...params, sorter, filter})}
        columns={columns}
        rowSelection={{
        onChange: (_, selectedRows) => setSelectedRows(selectedRows),
      }}
        />
        {selectedRowsState?.length > 0 && (
          <FooterToolbar
            extra={
              <div>
                已选择 <a style={{fontWeight: 600}}>{selectedRowsState.length}</a> 项&nbsp;&nbsp;
                <span>
                被禁用的作业共 {selectedRowsState.length - selectedRowsState.reduce((pre, item) => pre + (item.enabled ? 1 : 0), 0)} 人
              </span>
              </div>
            }
          >
            <Button type="primary" danger
                    onClick ={()=>{
                      Modal.confirm({
                        title: '删除作业',
                        content: '确定删除选中的作业吗？',
                        okText: '确认',
                        cancelText: '取消',
                        onOk:async () => {
                          await handleRemove(selectedRowsState);
                          setSelectedRows([]);
                          actionRef.current?.reloadAndRest?.();
                        }
                      });
                    }}
            >
              批量删除
            </Button>
            <Button type="primary"
                    onClick ={()=>{
                      Modal.confirm({
                        title: '启用作业',
                        content: '确定启用选中的作业吗？',
                        okText: '确认',
                        cancelText: '取消',
                        onOk:async () => {
                          await updateEnabled(selectedRowsState, true);
                          setSelectedRows([]);
                          actionRef.current?.reloadAndRest?.();
                        }
                      });
                    }}
            >批量启用</Button>
            <Button danger
                    onClick ={()=>{
                      Modal.confirm({
                        title: '禁用作业',
                        content: '确定禁用选中的作业吗？',
                        okText: '确认',
                        cancelText: '取消',
                        onOk:async () => {
                          await updateEnabled(selectedRowsState, false);
                          setSelectedRows([]);
                          actionRef.current?.reloadAndRest?.();
                        }
                      });
                    }}
            >批量禁用</Button>
          </FooterToolbar>
        )}
        <CreateForm onCancel={() => handleModalVisible(false)} modalVisible={createModalVisible}>
          <ProTable<TaskTableListItem, TaskTableListItem>
          onSubmit={async (value) => {
          const success = await handleAddOrUpdate(value);
          if (success) {
            handleModalVisible(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
          rowKey="id"
          type="form"
          columns={columns}
          />
        </CreateForm>
        {formValues && Object.keys(formValues).length ? (
          <UpdateForm
            onSubmit={async (value) => {
              const success = await handleAddOrUpdate(value);
              if (success) {
                handleUpdateModalVisible(false);
                setFormValues({});
                if (actionRef.current) {
                  actionRef.current.reload();
                }
              }
            }}
            onCancel={() => {
              handleUpdateModalVisible(false);
              setFormValues({});
            }}
            updateModalVisible={updateModalVisible}
            values={formValues}
          />
        ) : null}

        <Drawer
          width={600}
          visible={!!row}
          onClose={() => {
            setRow(undefined);
          }}
          closable={false}
        >
          {row?.name && (
            <ProDescriptions<TaskTableListItem>
              column={2}
              title={row?.name}
              request={async () => ({
              data: row || {},
            })}
              params={{
              id: row?.name,
            }}
              columns={columns}
              />
              )}
        </Drawer>
    </PageContainer>
);
};

export default TaskTableList;
