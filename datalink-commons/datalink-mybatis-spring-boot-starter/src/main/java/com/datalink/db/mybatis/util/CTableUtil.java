package com.datalink.db.mybatis.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.CaseFormat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * CTable的工具类
 *
 * @author wenmo
 * @since 2021/5/3 20:23
 */
public class CTableUtil {

    /**
     * @Author qiwenkai
     * @Description 自动装载表格分页排序参数
     * @Date 2020/9/24
     * @Param [para, wrapper, camelToUnderscore, isDelete]
     **/
    public static void autoQuery(JsonNode para, QueryWrapper<?> wrapper, boolean camelToUnderscore, boolean isDelete) {
        String sortField = para.has("sortField")? para.get("sortField").asText(): null;
        String sortValue = para.has("sortValue")? para.get("sortValue").asText(): null;
        String searchField = para.has("searchField")? para.get("searchField").asText(): null;
        String searchValue = para.has("searchValue")? para.get("searchValue").asText(): null;
        //wrapper.last("1=1");
        if (isDelete) {
            if (camelToUnderscore) {
                wrapper.eq(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "isDelete"), 0);
            } else {
                wrapper.eq("isDelete", 0);
            }
        }
        if (searchField != null && !searchField.equals("") && searchValue != null && !searchValue.equals("")) {
            if(searchField.contains(",")){
                final String [] searchFields = searchField.split(",");
                wrapper.and(qw -> {
                    for (int i = 0; i < searchFields.length; i++) {
                        String itemField = searchFields[i];
                        if (camelToUnderscore) {
                            itemField = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemField);
                        }
                        if(i>0){
                            qw.or();
                        }
                        qw.like("a." + itemField, searchValue);
                    }
                });
            }else{
                if (camelToUnderscore) {
                    searchField = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, searchField);
                }
                if(!searchField.contains(".")) {
                    wrapper.like("a." + searchField, searchValue);
                }
            }

        }
        if (sortField != null && sortValue != null) {
            if (camelToUnderscore) {
                sortField = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortField);
            }
            if (sortValue.equals("desc")) {
                if(!sortField.contains(".")) {
                    wrapper.orderByDesc("a." + sortField);
                }
            } else {
                if(!sortField.contains(".")) {
                    wrapper.orderByAsc("a." + sortField);
                }
            }
        }
    }

    /**
     * @return void
     * @Author qiwenkai
     * @Description 自动装载表单查询参数
     * @Date 2020/9/24 13:58
     * @Param [wrapper, para, blackarr, writearr, camelToUnderscore]
     **/
    public static void autoSetFromPara(QueryWrapper<?> wrapper, JsonNode para, String[] blackarr, String[] writearr, boolean camelToUnderscore) {
        List<String> blacklist = Arrays.asList(blackarr);
        List<String> writelist = Arrays.asList(writearr);
        if (para.isObject())
        {
            Iterator<Map.Entry<String, JsonNode>> it = para.fields();
            while (it.hasNext())
            {
                Map.Entry<String, JsonNode> entry = it.next();
                String mapKey = entry.getKey();
                if (blacklist.indexOf(mapKey) == -1 || writelist.indexOf(mapKey) > -1) {
                    Object mapValue = entry.getValue();
                    if(mapValue!=null&&!mapValue.equals("")) {
                        if (camelToUnderscore) {
                            //wrapper.eq(CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE).convert(mapKey), mapValue);
                            wrapper.eq(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, mapKey), mapValue);
                        } else {
                            wrapper.eq(mapKey, mapValue);
                        }
                    }
                }
            }
        }
    }

    /**
     * @return void
     * @Author qiwenkai
     * @Description 默认表单黑白名单
     * @Date 2020/9/24 13:59
     * @Param [wrapper, para]
     **/
    public static void autoSetFromParaDefault(QueryWrapper<?> wrapper, JsonNode para) {
        final String[] blackarr = {"page", "limit", "sortField", "sortValue", "searchField", "searchValue"};
        final String[] writearr = {};
        autoSetFromPara(wrapper, para, blackarr, writearr, true);
    }

    /**
     * @return void
     * @Author qiwenkai
     * @Description 默认表格参数
     * @Date 2020/9/24 14:00
     * @Param [para, wrapper]
     **/
    public static void autoQueryDefalut(JsonNode para, QueryWrapper<?> wrapper) {
        autoQuery(para, wrapper, true, true);
    }

    /**
     * @return void
     * @Author qiwenkai
     * @Description layuitable默认调用方法
     * @Date 2020/9/24 14:00
     * @Param [para, wrapper]
     **/
    public static void autoQueryAndSetFormParaDefalut(JsonNode para, QueryWrapper<?> wrapper) {
        autoSetFromParaDefault(wrapper, para);
        autoQueryDefalut(para, wrapper);
    }

    public static void autoQueryAndSetFormParaCustom(JsonNode para, QueryWrapper<?> wrapper) {
        autoSetFromParaDefault(wrapper, para);
        autoQuery(para, wrapper, true, false);
    }
}
