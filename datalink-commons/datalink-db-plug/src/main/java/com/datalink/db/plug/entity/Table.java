package com.datalink.db.plug.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Table
 * @Author wenmo
 * @Date 2020/10/20 21:07
 **/
@Getter
@Setter
public class Table implements Serializable {
    private static final long serialVersionUID = 8175277550633280468L;
    private String name;
    private String schema;
    private String comment;
    private List<Column> columns;

    public Table() {
    }

    public Table(String name) {
        this.name = name;
    }

    public Table(String name, String schema) {
        this.name = name;
        this.schema = schema;
    }

    public Table(String name, String schema, List<Column> columns) {
        this.name = name;
        this.schema = schema;
        this.columns = columns;
    }

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }
}
