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
        parentId: props.values.parentId,
        name: props.values.name,
        url: props.values.url,
        path: props.values.path,
        pathMethod: props.values.pathMethod,
        css: props.values.css,
        sort: props.values.sort,
        type: props.values.type,
        enabled: props.values.enabled,
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
                        name="parentId"
                        label="父ID"
 rules={[{ required: true, message: '请输入父ID！' }]}                     >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="name"
                        label="名称"
 rules={[{ required: true, message: '请输入名称！' }]}                     >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="url"
                        label="Url"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="path"
                        label="路由"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="pathMethod"
                        label="方法"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="css"
                        label="样式"
                    >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="sort"
                        label="排序"
 rules={[{ required: true, message: '请输入排序！' }]}                     >
                        <Input placeholder="请输入" />
                    </FormItem>
                    <FormItem
                        name="type"
                        label="类型"
 rules={[{ required: true, message: '请输入类型！' }]}                     >
                        <Switch checkedChildren="启用" unCheckedChildren="禁用" defaultChecked
                                checked={formVals.type}/>
                    </FormItem>
                    <FormItem
                        name="enabled"
                        label="是否启用"
 rules={[{ required: true, message: '请输入是否启用！' }]}                     >
                        <Switch checkedChildren="启用" unCheckedChildren="禁用" defaultChecked
                                checked={formVals.enabled}/>
                    </FormItem>
                    <FormItem
                        name="tenantId"
                        label="租户字段"
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
            title="编辑菜单"
            visible={updateModalVisible}
            footer={renderFooter()}
            onCancel={() => handleUpdateModalVisible()}
        >
            <Form
                {...formLayout}
                form={form}
                initialValues={{
        id: formVals.id,
        parentId: formVals.parentId,
        name: formVals.name,
        url: formVals.url,
        path: formVals.path,
        pathMethod: formVals.pathMethod,
        css: formVals.css,
        sort: formVals.sort,
        type: formVals.type,
        enabled: formVals.enabled,
        tenantId: formVals.tenantId,
                }}
            >
                {renderContent(formVals)}
            </Form>
        </Modal>
    );
};

export default UpdateForm;
