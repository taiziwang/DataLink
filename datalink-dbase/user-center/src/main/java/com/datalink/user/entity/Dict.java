package com.datalink.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * 扩展字典
 *
 * @author wenmo
 * @since 2021-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dbase_dict")
@ApiModel(value="Dict", description="扩展字典")
public class Dict implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "字典ID")
      private Integer id;

    @ApiModelProperty(value = "字典编码")
    @NotNull(message = "字典编码不能为空", groups = {Save.class})
    private String code;

    @ApiModelProperty(value = "字典名")
    @NotNull(message = "字典名不能为空", groups = {Save.class})
    private String name;

    @ApiModelProperty(value = "类型")
    @NotNull(message = "类型不能为空", groups = {Save.class})
    private String type;

    @ApiModelProperty(value = "说明")
    private String note;

    @ApiModelProperty(value = "启用")
    @NotNull(message = "启用不能为空", groups = {Save.class})
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "租户ID")
    @NotNull(message = "租户ID不能为空", groups = {Save.class})
    private Integer tenantId;


}
