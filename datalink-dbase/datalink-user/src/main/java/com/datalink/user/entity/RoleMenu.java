package com.datalink.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.datalink.db.mybatis.annotation.Save;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单角色关系
 *
 * @author wenmo
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dbase_role_menu")
@ApiModel(value="RoleMenu", description="菜单角色关系")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "菜单角色ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色ID不能为空", groups = {Save.class})
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID")
    @NotNull(message = "菜单ID不能为空", groups = {Save.class})
    private Integer menuId;


}
