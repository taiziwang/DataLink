package com.datalink.db.entity;

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
 * DB
 *
 * @author wenmo
 * @since 2021-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dbase_db")
@ApiModel(value="Db", description="DB")
public class Db implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "DBID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "DB编码")
    @NotNull(message = "DB编码不能为空", groups = {Save.class})
    private String code;

    @ApiModelProperty(value = "名称")
    @NotNull(message = "名称不能为空", groups = {Save.class})
    private String name;

    @ApiModelProperty(value = "类型")
    @NotNull(message = "类型不能为空", groups = {Save.class})
    private Integer type;

    @ApiModelProperty(value = "分组-dbTag")
    @NotNull(message = "分组-dbTag不能为空", groups = {Save.class})
    private Integer tag;

    @ApiModelProperty(value = "Driver")
    private String driver;

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "Port")
    private Integer port;

    @ApiModelProperty(value = "Url")
    private String url;

    @ApiModelProperty(value = "用户")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "配置")
    private String setting;

    @ApiModelProperty(value = "版本")
    private String dbVersion;

    @ApiModelProperty(value = "说明")
    private String note;

    @ApiModelProperty(value = "启用")
    @NotNull(message = "启用不能为空", groups = {Save.class})
    private String enabled;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "租户")
    @NotNull(message = "租户不能为空", groups = {Save.class})
    private String tenantId;


}
