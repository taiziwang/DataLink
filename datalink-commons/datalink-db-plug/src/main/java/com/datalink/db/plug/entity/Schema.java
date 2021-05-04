package com.datalink.db.plug.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Schema
 * @Author wenmo
 * @Date 2020/10/21 10:41
 **/
@Getter
@Setter
public class Schema implements Serializable {

    private static final long serialVersionUID = 4369387432973375364L;
    private String name;
    private List<Table> tables = new ArrayList<>();

    public Schema(String name) {
        this.name = name;
    }

    public Schema(String name, List<Table> tables) {
        this.name = name;
        this.tables = tables;
    }
}
