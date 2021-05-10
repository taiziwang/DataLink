package com.datalink.uaa.controller;

import com.datalink.base.model.PageResult;
import com.datalink.base.model.Result;
import com.datalink.uaa.entity.Tenant;
import com.datalink.uaa.service.TenantService;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 * 租户信息 Controller
 *
 * @author wenmo
 * @since 2021-05-10
 */
@Slf4j
@Api(tags = "租户信息模块api")
@RestController
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    private TenantService tenantService;

    /**
     * 新增或者更新
     */

    @ApiOperation(value = "动态新增修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int(11)"),
            @ApiImplicitParam(name = "clientId", value = "应用标识", required = true , dataType = "varchar(32)"),
            @ApiImplicitParam(name = "resourceIds", value = "资源限定串(逗号分割)", required =  false , dataType = "varchar(256)"),
            @ApiImplicitParam(name = "clientSecret", value = "应用密钥(bcyt) 加密", required =  false , dataType = "varchar(256)"),
            @ApiImplicitParam(name = "clientSecretStr", value = "应用密钥(明文)", required =  false , dataType = "varchar(256)"),
            @ApiImplicitParam(name = "scope", value = "范围", required =  false , dataType = "varchar(256)"),
            @ApiImplicitParam(name = "authorizedGrantTypes", value = "5种oauth授权方式(authorization_code,password,refresh_token,client_credentials)", required =  false , dataType = "varchar(256)"),
            @ApiImplicitParam(name = "webServerRedirectUri", value = "回调地址 ", required =  false , dataType = "varchar(256)"),
            @ApiImplicitParam(name = "authorities", value = "权限", required =  false , dataType = "varchar(256)"),
            @ApiImplicitParam(name = "accessTokenValidity", value = "access_token有效期", required =  false , dataType = "int(11)"),
            @ApiImplicitParam(name = "refreshTokenValidity", value = "refresh_token有效期", required =  false , dataType = "int(11)"),
            @ApiImplicitParam(name = "additionalInformation", value = "附加信息", required =  false , dataType = "text"),
            @ApiImplicitParam(name = "autoapprove", value = "是否自动授权 是-true", required = true , dataType = "char(5)"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", required = true, dataType = "datetime"),
            @ApiImplicitParam(name = "updateTime", value = "更新时间", required = true, dataType = "datetime"),
            @ApiImplicitParam(name = "clientName", value = "应用名称", required =  false , dataType = "varchar(128)"),
    })
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Tenant tenant) throws Exception {
        return tenantService.saveOrUpdateTenant(tenant);
    }

    /**
     * 动态查询列表
     */
    @ApiOperation(value = "动态查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页起始位置", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "分页结束位置", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "sortField", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sortValue", value = "排序值", required = false, dataType = "String"),
            @ApiImplicitParam(name = "searchField", value = "搜索字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "searchValue", value = "搜索值", required = false, dataType = "String")
    })
    @PostMapping("/list")
    public PageResult<Tenant> listTenants(@RequestBody JsonNode para) {
        return tenantService.selectForCTable(para);
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ID数组字符串", required = true, dataType = "String")
    })
    @DeleteMapping(value = "")
    public Result deleteMul(String ids) {
        if(ids==null||ids.equals("")){
            return Result.failed("请选择要删除的记录");
        }
        String[] idstrs = ids.split(",");
        List<Integer> error = new ArrayList<>();
        for (int i = 0; i < idstrs.length; i++) {
            Integer id =Integer.valueOf(idstrs[i]);
            if(!tenantService.removeById(id)){
                error.add(id);
            }
        }
        if(error.size()==0) {
        return Result.succeed("删除成功");
        }else{
        return Result.succeed("删除部分成功，但"+error.toString()+"删除失败，共"+error.size()+"次失败。");
        }
    }

    /**
     * 获取指定ID的信息
     */
    @ApiOperation(value = "获取指定ID的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
    })
    @PostMapping("/getOneById")
    public Result getOneById(@RequestBody Tenant tenant) throws Exception {
        tenant = tenantService.getById(tenant.getId());
        return Result.succeed(tenant,"获取成功");
    }
}

