package com.datalink.db.plug.config;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 跟包相关的配置项
 */

@Data
@Accessors(chain = true)
public class PackageConfig {

    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String parent = "com.baomidou";
    /**
     * 前端资源父包名。
     */
    private String resourceParent = "static";
    /**
     * Mapper.xml父包名。
     */
    private String mapperParent = "";
    /**
     * 父包模块名
     */
    private String moduleName = "";
    /**
     * 前端父包模块名
     */
    private String resourceModuleName = "";
    /**
     * 静态资源路由
     */
    private String htmlPath = "";
    /**
     * Entity包名
     */
    private String entity = "entity";
    /**
     * DTO包名
     */
    private String dto = "dto";
    /**
     * Service包名
     */
    private String service = "service";
    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";
    /**
     * Mapper包名
     */
    private String mapper = "mapper";
    /**
     * Mapper XML包名
     */
    private String xml = "mapper.xml";
    /**
     * Controller包名
     */
    private String controller = "controller";
    /**
     * updateForm包名
     */
    private String updateForm = "components";
    /**
     * index包名
     */
    private String index = "index";
    /**
     * datad包名
     */
    private String datad = "datad";
    /**
     * serviceTs包名
     */
    private String serviceTs = "servicets";
    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;

    /**
     * 父包名
     */
    public String getParent() {
        if (StringUtils.isNotBlank(moduleName)) {
            return parent + StringPool.DOT + moduleName;
        }
        return parent;
    }
    /**
     * 父包名
     */
    public String getResourceParent() {
        if (StringUtils.isNotBlank(resourceModuleName)) {
            return resourceParent + StringPool.SLASH + resourceModuleName;
        }
        return resourceParent;
    }
}
