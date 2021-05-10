package com.datalink.base.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单
 *
 * @author wenmo
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dbase_menu")
@ApiModel(value="Menu", description="菜单")
public class Menu extends SuperEntity {

    private static final long serialVersionUID = -2278423899883273677L;

    @ApiModelProperty(value = "父ID")
    private Integer parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "Url")
    private String url;

    @ApiModelProperty(value = "路由")
    private String path;

    @ApiModelProperty(value = "方法")
    private String pathMethod;

    @ApiModelProperty(value = "样式")
    private String css;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "类型")
    private Boolean type;

    @ApiModelProperty(value = "租户字段")
    private String tenantId;


}
