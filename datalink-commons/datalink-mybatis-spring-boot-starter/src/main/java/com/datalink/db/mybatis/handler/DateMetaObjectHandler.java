package com.datalink.db.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.datalink.db.mybatis.properties.MybatisPlusFillProperties;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 填充公共表字段
 * @Author wenmo
 * @Date 2021/5/3 14:12
 */
public class DateMetaObjectHandler implements MetaObjectHandler {
    private MybatisPlusFillProperties mybatisPlusFillProperties;

    public DateMetaObjectHandler(MybatisPlusFillProperties mybatisPlusFillProperties) {
        this.mybatisPlusFillProperties = mybatisPlusFillProperties;
    }

    @Override
    public boolean openInsertFill() {
        return mybatisPlusFillProperties.getEnableInsertFill();
    }

    @Override
    public boolean openUpdateFill() {
        return mybatisPlusFillProperties.getEnableUpdateFill();
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(mybatisPlusFillProperties.getCreateTimeField(), metaObject);
        Object updateTime = getFieldValByName(mybatisPlusFillProperties.getUpdateTimeField(), metaObject);
        if (createTime == null) {
            setFieldValByName(mybatisPlusFillProperties.getCreateTimeField(), LocalDateTime.now(), metaObject);
        }
        if (updateTime == null) {
            setFieldValByName(mybatisPlusFillProperties.getUpdateTimeField(), LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName(mybatisPlusFillProperties.getUpdateTimeField(), LocalDateTime.now(), metaObject);
    }
}
