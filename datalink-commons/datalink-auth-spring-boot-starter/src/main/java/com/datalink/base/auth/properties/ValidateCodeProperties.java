package com.datalink.base.auth.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * ValidateCodeProperties
 *
 * @author wenmo
 * @since 2021/5/11 19:41
 */
@Setter
@Getter
public class ValidateCodeProperties {
    /**
     * 设置认证通时不需要验证码的clientId
     */
    private String[] ignoreClientCode = {};
}

