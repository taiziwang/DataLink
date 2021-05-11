package com.datalink.uaa.service;

import com.datalink.base.model.PageResult;
import com.datalink.uaa.entity.TokenVo;

import java.util.Map;

/**
 * ITokensService
 *
 * @author wenmo
 * @since 2021/5/11
 */
public interface ITokensService {
    /**
     * 查询token列表
     * @param params 请求参数
     * @param clientId 应用id
     */
    PageResult<TokenVo> listTokens(Map<String, Object> params, String clientId);
}
