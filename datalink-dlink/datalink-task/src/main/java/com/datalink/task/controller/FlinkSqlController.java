package com.datalink.task.controller;

import com.datalink.base.model.ProTableResult;
import com.datalink.base.model.Result;
import com.datalink.task.entity.FlinkSql;
import com.datalink.task.service.FlinkSqlService;

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
 * FlinkSql Controller
 *
 * @author wenmo
 * @since 2021-05-24
 */
@Slf4j
@Api(tags = "FlinkSql模块api")
@RestController
@RequestMapping("/flinkSql")
public class FlinkSqlController {
    @Autowired
    private FlinkSqlService flinkSqlService;

    /**
     * 新增或者更新
     */

    @ApiOperation(value = "动态新增修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "int(11)"),
            @ApiImplicitParam(name = "name", value = "名称", required =  false , dataType = "varchar(50)"),
            @ApiImplicitParam(name = "alias", value = "别名", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "type", value = "类型", required =  false , dataType = "varchar(50)"),
            @ApiImplicitParam(name = "index", value = "次序", required =  false , dataType = "int(2)"),
            @ApiImplicitParam(name = "statement", value = "语句", required =  false , dataType = "text"),
            @ApiImplicitParam(name = "note", value = "备注", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "enabled", value = "是否启用", required = true , dataType = "tinyint(1)"),
            @ApiImplicitParam(name = "createUser", value = "创建人", required =  false , dataType = "int(11)"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", required = true, dataType = "datetime"),
            @ApiImplicitParam(name = "updateUser", value = "更新人", required =  false , dataType = "int(11)"),
            @ApiImplicitParam(name = "updateTime", value = "更新时间", required = true, dataType = "datetime"),
            @ApiImplicitParam(name = "taskId", value = "任务ID", required = true , dataType = "int(11)"),
            @ApiImplicitParam(name = "tenantId", value = "租户ID", required =  false , dataType = "varchar(32)"),
    })
    @PutMapping
    public Result saveOrUpdate(@RequestBody FlinkSql flinkSql) throws Exception {
        return flinkSqlService.saveOrUpdateFlinkSql(flinkSql);
    }

    /**
     * 动态查询列表
     */
    @ApiOperation(value = "动态查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页记录数", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "sort", value = "排序字段", required = false, dataType = "String"),
            @ApiImplicitParam(name = "filter", value = "排序值", required = false, dataType = "String"),
    })
    @PostMapping
    public ProTableResult<FlinkSql> listFlinkSqls(@RequestBody JsonNode para) {
        return flinkSqlService.selectForProTable(para);
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @DeleteMapping
    public Result deleteMul(@RequestBody JsonNode para) {
        if (para.size()>0){
            boolean isAdmin = false;
            List<Integer> error = new ArrayList<>();
            for (final JsonNode item : para){
                Integer id = item.asInt();
                if(!flinkSqlService.removeById(id)){
                    error.add(id);
                }
            }
            if(error.size()==0&&!isAdmin) {
                return Result.succeed("删除成功");
            }else {
                return Result.succeed("删除部分成功，但"+error.toString()+"删除失败，共"+error.size()+"次失败。");
            }
        }else{
            return Result.failed("请选择要删除的记录");
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
    public Result getOneById(@RequestBody FlinkSql flinkSql) throws Exception {
        flinkSql = flinkSqlService.getById(flinkSql.getId());
        return Result.succeed(flinkSql,"获取成功");
    }
}

