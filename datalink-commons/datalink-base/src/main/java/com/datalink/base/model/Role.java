package com.datalink.base.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 *
 * @author wenmo
 * @since 2021-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dbase_role")
@ApiModel(value="Role", description="角色")
public class Role extends SuperEntity {

    private static final long serialVersionUID = -1503321049194819584L;

    @ApiModelProperty(value = "角色编码")
    private String code;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "租户")
    private String tenantId;

    @TableField(exist = false)
    private Long userId;

}
