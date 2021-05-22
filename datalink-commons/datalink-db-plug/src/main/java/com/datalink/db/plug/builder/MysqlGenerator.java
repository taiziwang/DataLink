package com.datalink.db.plug.builder;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.datalink.db.plug.AutoGenerator;
import com.datalink.db.plug.config.*;
import com.datalink.db.plug.config.po.TableFill;
import com.datalink.db.plug.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器
 * @author wenmo
 * @since 2020/9/17 10:51
 */
public class MysqlGenerator {
    public static void main(String[] args) {

        String modulePath = "/datalink-dbase/datalink-user";
        String packageName = "user";
        String author = "wenmo";
        String tablePrefix = "dbase_";
        String[] includes = new String[]{"dbase_menu"};
        String projectPath = System.getProperty("user.dir");
        String modulePre = "com.datalink.";

        AutoGenerator mpg = new AutoGenerator();

        GlobalConfig gc = new GlobalConfig();
        gc.setFileOverride(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);
        gc.setOutputDir(projectPath + modulePath +"/src/main");
        gc.setAuthor(author);
        gc.setSwagger2(true);
        gc.setOpen(false);
        gc.setServiceName("%sService");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        /*gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setEntityName("%sVo");*/
        gc.setEntityName("%s");

        mpg.setGlobalConfig(gc);

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/datalink?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&autoReconnect=true");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("password");

        mpg.setDataSource(dsc);

        PackageConfig pc = new PackageConfig();
        //pc.setModuleName("sys"); // 模块名称, 这里可以根据不同模块来写
        pc.setParent(modulePre+packageName); // 父包名
        pc.setController("controller");
        pc.setDto("dto");
        pc.setMapper("dao");
        pc.setXml("mapper");
        pc.setUpdateForm("");
        pc.setIndex("");
        pc.setDatad("");
        pc.setServiceTs("");
        pc.setResourceParent("static/pages");
        pc.setResourceModuleName("html");

        mpg.setPackageInfo(pc);

        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(true);
        /*strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);*/
        //strategy.setSuperMapperClass(null);
        strategy.setEntityLombokModel(true);
        strategy.setEntityNotNullEnable(true);
        strategy.setSuperServiceClass(modulePre+"db.mybatis.service.ISuperService");
        strategy.setSuperServiceImplClass(modulePre+"db.mybatis.service.impl.SuperServiceImpl");
        strategy.setSuperMapperClass(modulePre+"db.mybatis.mapper.SuperMapper");
        List<TableFill> tableFills = new ArrayList<>();
        tableFills.add(new TableFill("create_time", FieldFill.INSERT));
        tableFills.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        tableFills.add(new TableFill("is_delete", FieldFill.INSERT));
        strategy.setTableFillList(tableFills);
        strategy.setTablePrefix(tablePrefix);
        /*strategy.setFieldPrefix("");*/
        strategy.setInclude(includes);
        mpg.setStrategy(strategy);
        TemplateConfig templateConfig = new TemplateConfig();

        /*templateConfig.setEntity("templates/entity.java");
        templateConfig.setMapper("templates/mapper.java");
        templateConfig.setXml("templates/mapper.xml");
        templateConfig.setService("templates/service.java");
        templateConfig.setServiceImpl("templates/serviceImpl.java");
        templateConfig.setController("templates/controller.java");
        templateConfig.setForm("templates/form.html");*/
        mpg.setTemplate(templateConfig);
        mpg.execute();
        System.out.println("------生成完成------");
    }

}
