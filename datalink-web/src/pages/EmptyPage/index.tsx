import { PageContainer } from '@ant-design/pro-layout';
import React, { useState, useEffect } from 'react';
import { Spin } from 'antd';
import styles from './index.less';
import FlinkSqlEditor from '@/components/Flink/FlinkSqlEdit';
// import MonacoEditor from 'react-monaco-editor';


export default () => {
  const [loading, setLoading] = useState<boolean>(true);
  const code:string = 'select count(1) from ';
/*  const options = {
    selectOnLineNumbers: true,
    renderSideBySide: false
  };*/
  useEffect(() => {
    setTimeout(() => {
      setLoading(false);
    }, 3000);
  }, []);
  return (
    <PageContainer content="这是一个新页面，从这里进行开发！" className={styles.main}>
      <div style={{ paddingTop: 100, textAlign: 'center' }}>
        <Spin spinning={loading} size="large" />
      </div>
      <div style = {{height:600}}>
        <FlinkSqlEditor
          value = {code}
        />
      </div>
    </PageContainer>
  );
};
