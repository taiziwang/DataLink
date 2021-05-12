package com.datalink.gateway.feign;

import com.datalink.base.constant.ServiceNameConstant;
import com.datalink.base.model.Menu;
import com.datalink.gateway.feign.fallback.MenuServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
/**
 * MenuService
 * 认证成功处理类
 *
 * @author wenmo
 * @since 2021/5/12
 */
@FeignClient(name = ServiceNameConstant.USER_SERVICE, fallbackFactory = MenuServiceFallbackFactory.class, decode404 = true)
public interface MenuService {
	/**
	 * 角色菜单列表
	 * @param roleCodes
	 */
	@GetMapping(value = "/menus/{roleCodes}")
	List<Menu> findByRoleCodes(@PathVariable("roleCodes") String roleCodes);
}
