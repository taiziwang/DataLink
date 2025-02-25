package com.dlink.controller;

import com.dlink.common.result.ProTableResult;
import com.dlink.common.result.Result;
import com.dlink.dto.CatalogueTaskDTO;
import com.dlink.model.Catalogue;
import com.dlink.service.CatalogueService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * CatalogueController
 *
 * @author wenmo
 * @since 2021/5/28 14:03
 **/
@Slf4j
@RestController
@RequestMapping("/api/catalogue")
public class CatalogueController {
    @Autowired
    private CatalogueService catalogueService;

    /**
     * 新增或者更新
     */
    @PutMapping
    public Result saveOrUpdate(@RequestBody Catalogue catalogue) throws Exception {
        if(catalogueService.saveOrUpdate(catalogue)){
            return Result.succeed("创建成功");
        }else {
            return Result.failed("创建失败");
        }
    }

    /**
     * 动态查询列表
     */
    @PostMapping
    public ProTableResult<Catalogue> listCatalogues(@RequestBody JsonNode para) {
        return catalogueService.selectForProTable(para);
    }

    /**
     * 批量删除
     */
    @DeleteMapping
    public Result deleteMul(@RequestBody JsonNode para) {
        if (para.size()>0){
            boolean isAdmin = false;
            List<Integer> error = new ArrayList<>();
            for (final JsonNode item : para){
                Integer id = item.asInt();
                if(!catalogueService.removeCatalogueAndTaskById(id)){
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
    @PostMapping("/getOneById")
    public Result getOneById(@RequestBody Catalogue catalogue) throws Exception {
        catalogue = catalogueService.getById(catalogue.getId());
        return Result.succeed(catalogue,"获取成功");
    }

    /**
     * 获取所有目录
     */
    @PostMapping("/getCatalogueTreeData")
    public Result getCatalogueTreeData() throws Exception {
        List<Catalogue> catalogues = catalogueService.getAllData();
        return Result.succeed(catalogues,"获取成功");
    }

    /**
     * 创建节点和作业
     */
    @PutMapping("/createTask")
    public Result createTask(@RequestBody CatalogueTaskDTO catalogueTaskDTO) throws Exception {
        Catalogue catalogue = catalogueService.createCatalogueAndTask(catalogueTaskDTO);
        if(catalogue.getId()!=null){
            return Result.succeed(catalogue,"创建成功");
        }else {
            return Result.failed("创建失败");
        }
    }

    /**
     * 创建节点和作业
     */
    @PutMapping("/toRename")
    public Result toRename(@RequestBody Catalogue catalogue) throws Exception {
        if(catalogueService.toRename(catalogue)){
            return Result.succeed("重命名成功");
        }else {
            return Result.failed("重命名失败");
        }
    }
}
