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
    name: 'home',
    icon: 'home',
    component: './Welcome',
  },
  {
    path: '/dbase',
    name: 'dbase',
    access: 'canAdmin',
    routes: [
      {
        path: '/dbase/usercenter',
        name: 'usercenter',
        icon: 'user',
        routes: [
          {
            path: '/dbase/usercenter/user',
            name: 'user',
            icon: 'user',
            component: './Dbase/User',
          },
          {
            path: '/dbase/usercenter/role',
            name: 'role',
            icon: 'smile',
            component: './Common/Build',
          },
          {
            path: '/dbase/usercenter/menu',
            name: 'menu',
            icon: 'smile',
            component: './Common/Build',
          },
          {
            path: '/dbase/usercenter/client',
            name: 'client',
            icon: 'smile',
            component: './Common/Build',
          },
          {
            path: '/dbase/usercenter/token',
            name: 'token',
            icon: 'smile',
            component: './Common/Build',
          },
        ],
      },
      {
        path: '/dbase/database',
        name: 'database',
        icon: 'database',
        component: './Welcome',
      },
    ],
  },
  {
    path: '/dbus',
    name: 'dbus',
    component: './Common/Build',
  },
  {
    path: '/dlink',
    name: 'dlink',
    access: 'canAdmin',
    routes: [
      {
        path: '/dlink/flink',
        name: 'flink',
        icon: 'smile',
        routes: [
          {
            path: '/dlink/flink/sqldev',
            name: 'sqldev',
            icon: 'smile',
            component: './EmptyPage',
          },
          {
            name: 'sqldev',
            icon: 'smile',
            path: '/dlink/flink/edit',
            component: './Edit',
          },
          {
            name: 'sqldev',
            icon: 'smile',
            path: '/dlink/flink/edit2',
            component: './Edit2',
          },
        ],
      },
    ],
  },
  {
    path: '/dsink',
    name: 'dsink',
    component: './Common/Build',
  },
  {
    path: '/dview',
    name: 'dview',
    component: './Common/Build',
  },
  {
    path: '/dai',
    name: 'dai',
    component: './Common/Build',
  },
  {
    path: '/dev',
    name: 'dev',
    component: './Common/Build',
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
    path: '/demo',
    name: 'demo',
    //access: 'canAdmin',
    routes: [
      {
        name: 'list',
        icon: 'table',
        path: '/demo/list',
        routes: [
          {
            name: 'table-list',
            icon: 'table',
            path: '/demo/list/listtablelist',
            component: './Demo/ListTableList',
          },
          {
            name: 'basic-list',
            icon: 'smile',
            path: '/demo/list/listbasiclist',
            component: './Demo/ListBasicList',
          },
        ],
      },
      {
        name: 'form',
        icon: 'smile',
        path: '/demo/form',
        routes: [
          {
            name: 'advanced-form',
            icon: 'smile',
            path: '/demo/form/formadvancedform',
            component: './FormAdvancedForm',
          },
        ],
      },
    ],
  },

  {
    path: '/',
    redirect: '/welcome',
  },
  {
    component: './404',
  },
];
