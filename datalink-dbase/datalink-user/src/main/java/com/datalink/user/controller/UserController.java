package com.datalink.user.controller;

import com.datalink.base.model.PageResult;
import com.datalink.base.model.Result;
import com.datalink.base.model.User;
import com.datalink.user.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 Controller
 *
 * @author wenmo
 * @since 2021-05-03
 */
@Slf4j
@Api(tags = "用户模块api")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final String ADMIN_CHANGE_MSG = "超级管理员不给予修改";
    private static final Integer ADMIN_CODE = 1;

    @Autowired
    private UserService userService;

    /**
     * 动态新增修改用户
     */
    @ApiOperation(value = "动态新增修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "nickname", value = "姓名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "headUrl", value = "头像Url", required = false, dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "enabled", value = "是否启用", required = true, dataType = "Boolean"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", value = "更新时间", required = false, dataType = "Date")
    })
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody User user) throws Exception {
        if(user.getId()!=null&&checkAdmin(user.getId())){
            return Result.failed(ADMIN_CHANGE_MSG);
        }
        return userService.saveOrUpdateUser(user);
    }

    /**
     * 动态查询用户列表
     */
    @ApiOperation(value = "动态查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "页记录数", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "sortField", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sortValue", value = "排序值", required = false, dataType = "String"),
            @ApiImplicitParam(name = "searchField", value = "搜索字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "searchValue", value = "搜索值", required = false, dataType = "String")
    })
    @PostMapping("/list")
    public PageResult<User> listUsers(@RequestBody JsonNode para) {
        return userService.selectForCTable(para);
    }

    /**
     * 批量删除用户
     */
    @ApiOperation(value = "批量删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ID数组字符串", required = true, dataType = "String")
    })
    @DeleteMapping(value = "")
    public Result deleteMul(String ids) {
        if (ids==null||"".equals(ids)) {
            return Result.failed("请选择要删除的记录");
        }
        String[] idstrs = ids.split(",");
        List<Integer> error = new ArrayList<>();
        boolean isAdmin = false;
        for (int i = 0; i < idstrs.length; i++) {
            Integer id = Integer.valueOf(idstrs[i]);
            if(checkAdmin(id)){
                isAdmin = true;
                error.add(id);
                continue;
            }
            if(!userService.removeById(id)){
                error.add(id);
            }
        }
        if(error.size()==0&&!isAdmin) {
            return Result.succeed("删除成功");
        }else if(isAdmin) {
            return Result.succeed("删除部分成功，但"+error.toString()+"删除失败，共"+error.size()+"次失败，其中"+ADMIN_CHANGE_MSG+"。");
        }else {
            return Result.succeed("删除部分成功，但"+error.toString()+"删除失败，共"+error.size()+"次失败。");
        }
    }

    /**
     * 获取指定ID的用户信息
     */
    @ApiOperation(value = "获取指定ID的用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
    })
    @PostMapping("/getOneById")
    public Result getOneById(Integer id) {
        User user = userService.getById(id);
        return Result.succeed(user, "获取成功");
    }

    /**
     * 查询用户实体对象SysUser
     */
    @GetMapping(value = "/users/name/{username}")
    @ApiOperation(value = "根据用户名查询用户实体")
    @Cacheable(value = "user", key = "#username")
    public User selectByUsername(@PathVariable String username) {
        return userService.selectByUsername(username);
    }

    /**
     * 是否超级管理员
     */
    private boolean checkAdmin(Integer id) {
        return ADMIN_CODE.equals(id);
    }
}

