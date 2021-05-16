export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: '/user',
        routes: [
          {
            name: 'login',
            path: '/user/login',
            component: './user/Login',
          },
        ],
      },
    ],
  },
  {
    path: '/welcome',
    name: 'welcome',
    icon: 'smile',
    component: './Welcome',
  },
  {
    path: '/dbase',
    name: 'Dbase 资源管理',
    access: 'canAdmin',
    component: './Welcome',
    routes: [
      {
        path: '/dbase/db',
        name: '数据源中心',
        icon: 'database',
        component: './Welcome',
      },
    ],
  },
  {
    path: '/dlink',
    name: 'Dlink 数据治理',
    access: 'canAdmin',
    //component: './Welcome',
    routes: [
      {
        path: '/dlink/flink',
        name: 'Flink中心',
        icon: 'smile',
        routes: [
          {
            path: '/dlink/flink/flinksqldev',
            name: 'Flink Sql 开发',
            icon: 'smile',
            component: './Welcome',
          },
          {
            name: '高级表单',
            icon: 'smile',
            path: '/dlink/flink/formadvancedform',
            component: './FormAdvancedForm',
          },
        ],
      },
    ],
  },
  {
    path: '/admin',
    name: 'admin',
    icon: 'crown',
    access: 'canAdmin',
    component: './Admin',
    routes: [
      {
        path: '/admin/sub-page',
        name: 'sub-page',
        icon: 'smile',
        component: './Welcome',
      },
    ],
  },
  {
    name: 'list.table-list',
    icon: 'table',
    path: '/listtablelist',
    component: './ListTableList',
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    component: './404',
  },
];
