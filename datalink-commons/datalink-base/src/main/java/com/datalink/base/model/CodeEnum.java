package com.datalink.base.model;

/**
 * 状态码
 *
 * @author wenmo
 * @since 2021/5/3 19:58
 */
public enum CodeEnum {
    SUCCESS(0),
    ERROR(1);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
