package com.datalink.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.datalink.db.mybatis.annotation.Save;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单
 *
 * @author wenmo
 * @since 2021-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dbase_menu")
@ApiModel(value="Menu", description="菜单")
public class Menu implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "菜单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父ID")
    @NotNull(message = "父ID不能为空", groups = {Save.class})
    private Integer parentId;

    @ApiModelProperty(value = "名称")
    @NotNull(message = "名称不能为空", groups = {Save.class})
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
    @NotNull(message = "排序不能为空", groups = {Save.class})
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "类型")
    @NotNull(message = "类型不能为空", groups = {Save.class})
    private Boolean type;

    @ApiModelProperty(value = "是否启用")
    @NotNull(message = "是否启用不能为空", groups = {Save.class})
    private Boolean enabled;

    @ApiModelProperty(value = "租户字段")
    private String tenantId;


}
