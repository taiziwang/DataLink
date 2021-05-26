package com.datalink.task.controller;

import com.datalink.base.annotation.LoginUser;
import com.datalink.base.model.ProTableResult;
import com.datalink.base.model.Result;
import com.datalink.base.model.User;
import com.datalink.flink.result.SubmitResult;
import com.datalink.task.dto.FlinkSqlExecuteDTO;
import com.datalink.task.entity.Task;
import com.datalink.task.service.StudioService;
import com.datalink.task.service.TaskService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
public class StudioController {
    @Autowired
    private StudioService studioService;

    /**
     * Studio 执行Flink Sql
     */
    @PostMapping("/executeSql")
    public Result executeSql(@RequestBody FlinkSqlExecuteDTO flinkSqlExecuteDTO) throws Exception {
        return Result.succeed(studioService.executeSql(flinkSqlExecuteDTO),"执行成功");
    }


}

