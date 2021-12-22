import request from "umi-request";
import {TableListParams} from "@/components/Common/data";
import {message, Modal} from "antd";
import {submitTask} from "@/pages/Dlink/Task/service";

export async function queryData(url:string,params?: TableListParams) {
  return request(url, {
    method: 'POST',
    data: {
      ...params,
    },
  });
}

export async function removeData(url:string,params: number[]) {
  return request(url, {
    method: 'DELETE',
    data: {
      ...params,
    },
  });
}

export async function addOrUpdateData(url:string,params: any) {
  return request(url, {
    method: 'PUT',
    data: {
      ...params,
    },
  });
}

export async function postDataArray(url:string,params: number[]) {
  return request(url, {
    method: 'POST',
    data: {
      ...params,
    },
  });
}

export const handleAddOrUpdate = async (url:string,fields: any) => {
  const tipsTitle = fields.id ? "修改" : "添加";
  const hide = message.loading(`正在${tipsTitle}`);
  try {
    const {msg} = await addOrUpdateData(url,{...fields});
    hide();
    message.success(msg);
    return true;
  } catch (error) {
    hide();
    message.error(error);
    return false;
  }
};

export const handleRemove = async (url:string,selectedRows: []) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    const {msg} = await removeData(url,selectedRows.map((row) => row.id));
    hide();
    message.success(msg);
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试');
    return false;
  }
};

export const handleSubmit = async (url:string,title:string,selectedRows: []) => {
  const hide = message.loading('正在'+title);
  if (!selectedRows) return true;
  try {
    const {msg} = await postDataArray(url,selectedRows.map((row) => row.id));
    hide();
    message.success(msg);
    return true;
  } catch (error) {
    hide();
    message.error(title+'失败，请重试');
    return false;
  }
};

export const updateEnabled = (url:string,selectedRows: [], enabled: boolean) => {
  selectedRows.forEach((item) => {
    handleAddOrUpdate(url,{id: item.id, enabled: enabled})
  })
};

