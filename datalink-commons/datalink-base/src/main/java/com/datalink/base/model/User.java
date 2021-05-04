package com.datalink.base.model;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 *
 * @author wenmo
 * @since 2021-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dbase_user")
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "自增ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "登录名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "姓名")
    private String nickname;

    @ApiModelProperty(value = "头像Url")
    private String headUrl;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "性别")
    private Boolean sex;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    //@TableField(fill = FieldFill.INSERT)
    //@TableLogic
    private Boolean isDelete;


}
