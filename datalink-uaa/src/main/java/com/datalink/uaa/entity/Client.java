package com.datalink.uaa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.datalink.base.model.SuperEntity;
import com.datalink.db.mybatis.annotation.Save;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * Client
 *
 * @author wenmo
 * @since 2021/5/11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dbase_client")
public class Client extends SuperEntity {
   private static final long serialVersionUID = 4635390475875680554L;
   @ApiModelProperty(value = "应用标识")
   @NotNull(message = "应用标识不能为空", groups = {Save.class})
   private String clientId;
   @ApiModelProperty(value = "应用名称")
   private String clientName;
   @ApiModelProperty(value = "资源限定串(逗号分割)")
   private String resourceIds = "";
   @ApiModelProperty(value = "应用密钥(bcyt) 加密")
   private String clientSecret;
   @ApiModelProperty(value = "应用密钥(明文)")
   private String clientSecretStr;
   @ApiModelProperty(value = "范围")
   private String scope = "all";
   @ApiModelProperty(value = "5种oauth授权方式(authorization_code,password,refresh_token,client_credentials)")
   private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";
   @ApiModelProperty(value = "回调地址 ")
   private String webServerRedirectUri;
   @ApiModelProperty(value = "权限")
   private String authorities = "";
   @ApiModelProperty(value = "access_token有效期")
   @TableField(value = "access_token_validity")
   private Integer accessTokenValiditySeconds = 18000;
   @ApiModelProperty(value = "refresh_token有效期")
   @TableField(value = "refresh_token_validity")
   private Integer refreshTokenValiditySeconds = 28800;
   @ApiModelProperty(value = "附加信息")
   private String additionalInformation = "{}";
   @ApiModelProperty(value = "是否自动授权 是-true")
   @NotNull(message = "是否自动授权 是-true不能为空", groups = {Save.class})
   private String autoapprove = "true";
}
