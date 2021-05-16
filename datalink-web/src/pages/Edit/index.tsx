import React, {useEffect, useRef, useState} from 'react';
import * as monaco from 'monaco-editor/esm/vs/editor/editor.api.js';

/**
 *  2021年2月26日 13:20:14
 *  MonacoEditor 组件
 *  props详情 请看下方 const { style = .....}
 * */
function App(props) {
  const {
    style = { // dom节点样式
      height: '300px',
      width: '95%',
      border: '1px solid #eee',
    },
    value = '', // 代码文本
    onChange = () => { // 改变的事件
    },
    fontSize = 14, // 代码字体大小
    monacoOptions = {}, // monaco 自定义属性
    language = 'sql', // 语言 支持 js ts sql css json html等
  } = props;
  const editOrRef = useRef();
  const ThisEditor = useRef();
  useEffect(() => {
    ThisEditor.current = monaco.editor.create(editOrRef.current, {
      value: value || '',
      language,
      theme: "vs",
      fontSize: fontSize + 'px',
      minimap: { // 关闭代码缩略图
        enabled: false,
      },
      ...monacoOptions,
    });

    ThisEditor.current.onDidChangeModelContent((e) => {
      let newValue = ThisEditor.current.getValue();
      onChange(newValue);
    });
    return () => {
      ThisEditor.current.dispose();
      ThisEditor.current = undefined; // 清除编辑器对象
    }
  }, []);
  useEffect(() => {
    if (ThisEditor.current) {
      ThisEditor.current.updateOptions({
        fontSize: fontSize + 'px',
      })
    }

  }, [fontSize]);

  return (
    <div style={style}
         ref={editOrRef}
    >

    </div>
  );
}

export default App;
