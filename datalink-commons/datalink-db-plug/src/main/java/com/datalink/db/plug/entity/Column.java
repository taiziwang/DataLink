package com.datalink.db.plug.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Column
 * @Author wenmo
 * @Date 2020/10/20 21:01
 **/
@Setter
@Getter
public class Column implements Serializable {
    private static final long serialVersionUID = 5089416968459781518L;
    private boolean convert;
    private boolean keyFlag;
    /**
     * 主键是否为自增类型
     */
    private boolean keyIdentityFlag;
    private String name;
    private String type;
    private String propertyName;
    private String columnType;
    private String comment;
    private String fill;
    private String isNotNull;
    private boolean keyWords;
    private String columnName;
    private String columnFamily;

}
