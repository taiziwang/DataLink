package com.datalink.user.service;

import com.datalink.base.model.Result;
import com.datalink.base.model.Menu;
import com.datalink.db.mybatis.service.ISuperService;
/**
 * 菜单 服务类
 *
 * @author wenmo
 * @since 2021-05-10
 */
public interface MenuService extends ISuperService<Menu> {

    void saveMenu(Menu menu) throws Exception;

    Result saveOrUpdateMenu(Menu menu) throws Exception;
}
