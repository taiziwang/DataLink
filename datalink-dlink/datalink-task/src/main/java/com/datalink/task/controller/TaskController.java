package com.datalink.task.controller;

import com.datalink.base.annotation.LoginUser;
import com.datalink.base.model.ProTableResult;
import com.datalink.base.model.Result;
import com.datalink.base.model.User;
import com.datalink.flink.result.SubmitResult;
import com.datalink.task.entity.Task;
import com.datalink.task.service.TaskService;

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
 * 任务 Controller
 *
 * @author wenmo
 * @since 2021-05-24
 */
@Slf4j
@Api(tags = "任务模块api")
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * 新增或者更新
     */

    @ApiOperation(value = "动态新增修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "int(11)"),
            @ApiImplicitParam(name = "name", value = "名称", required = true , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "alias", value = "别名", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "type", value = "类型", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "note", value = "注释", required =  false , dataType = "varchar(255)"),
            @ApiImplicitParam(name = "enabled", value = "是否启用", required = true , dataType = "tinyint(1)"),
            @ApiImplicitParam(name = "createUser", value = "创建人", required =  false , dataType = "int(11)"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", required = true, dataType = "datetime"),
            @ApiImplicitParam(name = "updateUser", value = "更新人", required =  false , dataType = "int(11)"),
            @ApiImplicitParam(name = "updateTime", value = "更新时间", required = true, dataType = "datetime"),
            @ApiImplicitParam(name = "tenantId", value = "租户", required =  false , dataType = "varchar(32)"),
    })
    @PutMapping
    public Result saveOrUpdate(@LoginUser User user, @RequestBody Task task) throws Exception {
        if(task.getId()==null){
            task.setCreateUser(user.getId());
        }
        task.setUpdateUser(user.getId());
        return taskService.saveOrUpdateTask(task);
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
    public ProTableResult<Task> listTasks(@RequestBody JsonNode para) {
        return taskService.selectForProTable(para);
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
                if(!taskService.removeById(id)){
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
     * 批量执行
     */
    @ApiOperation(value = "批量执行")
    @PostMapping(value = "/submit")
    public Result submit(@RequestBody JsonNode para) throws Exception {
        if (para.size()>0){
            List<SubmitResult> results = new ArrayList<>();
            List<Integer> error = new ArrayList<>();
            for (final JsonNode item : para){
                Integer id = item.asInt();
                SubmitResult result = taskService.submitByTaskId(id);
                if(!result.isSuccess()){
                    error.add(id);
                }
                results.add(result);
            }
            if(error.size()==0) {
                return Result.succeed(results,"删除成功");
            }else {
                return Result.succeed(results,"删除部分成功，但"+error.toString()+"删除失败，共"+error.size()+"次失败。");
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
    public Result getOneById(@RequestBody Task task) throws Exception {
        task = taskService.getById(task.getId());
        return Result.succeed(task,"获取成功");
    }
}

