package com.datalink.base.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户
 *
 * @author wenmo
 * @since 2021-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dbase_user")
//@ApiModel(value="User对象", description="用户")
public class User extends SuperEntity {

    private static final long serialVersionUID = 3472647372575082199L;

//    @ApiModelProperty(value = "登录名")
    private String username;

//    @ApiModelProperty(value = "密码")
    private String password;

//    @ApiModelProperty(value = "姓名")
    private String nickname;

//    @ApiModelProperty(value = "头像Url")
    private String avatar;

//    @ApiModelProperty(value = "手机号")
    private String mobile;

//    @ApiModelProperty(value = "性别")
    private Boolean sex;

//    @ApiModelProperty(value = "OpenID")
    private String openId;
//    @ApiModelProperty(value = "是否删除")
    //@TableField(fill = FieldFill.INSERT)
    //@TableLogic
    private Boolean isDelete;

//    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @TableField(exist = false)
    private List<Role> roles;
    @TableField(exist = false)
    private String roleId;
    @TableField(exist = false)
    private String oldPassword;
    @TableField(exist = false)
    private String newPassword;
}
