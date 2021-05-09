package com.datalink.db.controller;

import com.datalink.base.model.PageResult;
import com.datalink.base.model.Result;
import com.datalink.db.entity.Db;
import com.datalink.db.service.DbService;

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
 * DB Controller
 *
 * @author wenmo
 * @since 2021-05-09
 */
@Slf4j
@Api(tags = "DB模块api")
@RestController
@RequestMapping("/db")
public class DbController {
    @Autowired
    private DbService dbService;

    /**
     * 新增或者更新
     */

    @ApiOperation(value = "动态新增修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "DBID", required = true, dataType = "int(11)"),
            @ApiImplicitParam(name = "code", value = "DB编码", required = true , dataType = "varchar(50)"),
            @ApiImplicitParam(name = "name", value = "名称", required = true , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "type", value = "类型", required = true , dataType = "int(11)"),
            @ApiImplicitParam(name = "tag", value = "分组-dbTag", required = true , dataType = "int(11)"),
            @ApiImplicitParam(name = "driver", value = "Driver", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "ip", value = "IP", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "port", value = "Port", required =  false , dataType = "int(11)"),
            @ApiImplicitParam(name = "url", value = "Url", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "username", value = "用户", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "password", value = "密码", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "setting", value = "配置", required =  false , dataType = "text"),
            @ApiImplicitParam(name = "dbVersion", value = "版本", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "note", value = "说明", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "enabled", value = "启用", required = true , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", required = true, dataType = "datetime"),
            @ApiImplicitParam(name = "updateTime", value = "更新时间", required = true, dataType = "datetime"),
            @ApiImplicitParam(name = "tenantId", value = "租户", required = true , dataType = "varchar(32)"),
    })
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Db db) throws Exception {
        return dbService.saveOrUpdateDb(db);
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
    public PageResult<Db> listDbs(@RequestBody JsonNode para) {
        return dbService.selectForCTable(para);
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
            if(!dbService.removeById(id)){
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
    public Result getOneById(@RequestBody Db db) throws Exception {
        db = dbService.getById(db.getId());
        return Result.succeed(db,"获取成功");
    }
}

