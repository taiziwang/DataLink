import React, {useEffect, useState} from 'react';
import { Form, Button, Input, Modal } from 'antd';

import type { TableListItem } from '../data.d';
import Switch from "antd/es/switch";
export type UpdateFormProps = {
    onCancel: (flag?: boolean, formVals?: Partial<TableListItem>) => void;
    onSubmit: (values: Partial<TableListItem>) => void;
    updateModalVisible: boolean;
    values: Partial<TableListItem>;
};
const FormItem = Form.Item;

const formLayout = {
    labelCol: { span: 7 },
    wrapperCol: { span: 13 },
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
    const [formVals, setFormVals] = useState<Partial<TableListItem>>({
        id: props.values.id,
        name: props.values.name,
        alias: props.values.alias,
        type: props.values.type,
        index: props.values.index,
        statement: props.values.statement,
        note: props.values.note,
        enabled: props.values.enabled,
        createUser: props.values.createUser,
        updateUser: props.values.updateUser,
        taskId: props.values.taskId,
        tenantId: props.values.tenantId,
    });

    const [form] = Form.useForm();

    const {
        onSubmit: handleUpdate,
        onCancel: handleUpdateModalVisible,
        updateModalVisible,
        values,
    } = props;

    const submitForm = async () => {
        const fieldsValue = await form.validateFields();
        setFormVals({ ...formVals, ...fieldsValue });
        handleUpdate({ ...formVals, ...fieldsValue });
    };

    const renderContent = (formVals) => {
        return (
            <>
                    <FormItem
                        name="name"
                        label="名称"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="alias"
                        label="别名"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="type"
                        label="类型"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="index"
                        label="次序"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="statement"
                        label="语句"
                    >
                    </FormItem>
                    <FormItem
                        name="note"
                        label="备注"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="enabled"
                        label="是否启用"
 rules={[{ required: true, message: '请输入是否启用！' }]}                     >
                        <Switch checkedChildren="启用" unCheckedChildren="禁用" defaultChecked
                                checked={formVals.enabled}/>
                    </FormItem>
                    <FormItem
                        name="createUser"
                        label="创建人"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="updateUser"
                        label="更新人"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="taskId"
                        label="任务ID"
 rules={[{ required: true, message: '请输入任务ID！' }]}                     >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="tenantId"
                        label="租户ID"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
            </>
        );
    };

    const renderFooter = () => {
        return (
            <>
                <Button onClick={() => handleUpdateModalVisible(false, values)}>取消</Button>
                <Button type="primary" onClick={() => submitForm()}>
                    完成
                </Button>
            </>
        );
    };

    return (
        <Modal
            width={640}
            bodyStyle={{ padding: '32px 40px 48px' }}
            destroyOnClose
            title="编辑FlinkSql"
            visible={updateModalVisible}
            footer={renderFooter()}
            onCancel={() => handleUpdateModalVisible()}
        >
            <Form
                {...formLayout}
                form={form}
                initialValues={{
        id: formVals.id,
        name: formVals.name,
        alias: formVals.alias,
        type: formVals.type,
        index: formVals.index,
        statement: formVals.statement,
        note: formVals.note,
        enabled: formVals.enabled,
        createUser: formVals.createUser,
        updateUser: formVals.updateUser,
        taskId: formVals.taskId,
        tenantId: formVals.tenantId,
                }}
            >
                {renderContent(formVals)}
            </Form>
        </Modal>
    );
};

export default UpdateForm;
