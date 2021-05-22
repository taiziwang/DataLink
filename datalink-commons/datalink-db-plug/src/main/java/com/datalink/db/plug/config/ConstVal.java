package com.datalink.db.plug.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.nio.charset.StandardCharsets;

/**
 * 定义常量
 */
public interface ConstVal {

    String MODULE_NAME = "ModuleName";
    String PACKAGE_NAME = "PackageName";
    String JAVA_BUILD_PATH = "/java";
    String RESOURCES_BUILD_PATH = "/resources";

    String ENTITY = "Entity";
    String DTO = "DTO";
    String SERVICE = "Service";
    String SERVICE_IMPL = "ServiceImpl";
    String MAPPER = "Mapper";
    String XML = "Xml";
    String CONTROLLER = "Controller";
    String UPDATEFORM = "updateForm";
    String INDEX = "index";
    String DATAD = "data.d";
    String SERVICE_TS = "service";
    String HTML_PATH = "HtmlPath";
    String HTML_ID = "HtmlID";

    String ENTITY_PATH = "entity_path";
    String DTO_PATH = "dto_path";
    String SERVICE_PATH = "service_path";
    String SERVICE_IMPL_PATH = "service_impl_path";
    String MAPPER_PATH = "mapper_path";
    String XML_PATH = "xml_path";
    String CONTROLLER_PATH = "controller_path";
    String FORM_PATH = "form_path";
    String LIST_PATH = "list_path";
    String DATAD_PATH = "datad_path";
    String SERVICE_TS_PATH = "service_ts_path";

    String JAVA_TMPDIR = "java.io.tmpdir";
    String UTF8 = StandardCharsets.UTF_8.name();
    String UNDERLINE = "_";

    String JAVA_SUFFIX = StringPool.DOT_JAVA;
    String KT_SUFFIX = ".kt";
    String XML_SUFFIX = ".xml";
    String HTML_SUFFIX = ".tsx";
    String TS_SUFFIX = ".ts";

    String TEMPLATE_ENTITY_JAVA = "/templates/entity.java";
    String TEMPLATE_ENTITY_KT = "/templates/entity.kt";
    String TEMPLATE_DTO = "/templates/dto.java";
    String TEMPLATE_MAPPER = "/templates/mapper.java";
    String TEMPLATE_XML = "/templates/mapper.xml";
    String TEMPLATE_SERVICE = "/templates/service.java";
    String TEMPLATE_SERVICE_IMPL = "/templates/serviceImpl.java";
    String TEMPLATE_CONTROLLER = "/templates/controller.java";
    String TEMPLATE_FORM = "/templates/updateForm.tsx";
    String TEMPLATE_LIST = "/templates/index.tsx";
    String TEMPLATE_DATAD = "/templates/data.d.ts";
    String TEMPLATE_SERVICE_TS = "/templates/service.ts";

    String VM_LOAD_PATH_KEY = "file.resource.loader.class";
    String VM_LOAD_PATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";

    String SUPER_MAPPER_CLASS = "com.baomidou.mybatisplus.core.mapper.BaseMapper";
    String SUPER_SERVICE_CLASS = "com.baomidou.mybatisplus.extension.service.IService";
    String SUPER_SERVICE_IMPL_CLASS = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";

}
