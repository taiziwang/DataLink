package com.datalink.db.plug.config.po;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;

/**
 * 表名拼接
 */
public class LikeTable {

    private String value;

    private SqlLike like = SqlLike.DEFAULT;

    public LikeTable(String value) {
        this.value = value;
    }

    public LikeTable(String value, SqlLike like) {
        this.value = value;
        this.like = like;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public String getValue() {
        return SqlUtils.concatLike(this.value, like);
    }

}
