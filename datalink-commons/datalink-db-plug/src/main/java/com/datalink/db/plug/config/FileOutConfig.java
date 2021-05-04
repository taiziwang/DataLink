package com.datalink.db.plug.config;

import com.datalink.db.plug.config.po.TableInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 输出文件配置
 */
@Data
@Accessors(chain = true)
public abstract class FileOutConfig {

    /**
     * 模板
     */
    private String templatePath;

    public FileOutConfig() {
        // to do nothing
    }

    public FileOutConfig(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * 输出文件
     */
    public abstract String outputFile(TableInfo tableInfo);
}
