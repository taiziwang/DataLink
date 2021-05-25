package com.datalink.task.entity;

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
 * FlinkSql
 *
 * @author wenmo
 * @since 2021-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dlink_flink_sql")
@ApiModel(value="FlinkSql", description="FlinkSql")
public class FlinkSql implements Serializable {

    private static final long serialVersionUID = 7673401781708064790L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "别名")
    private String alias;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "次序")
    private Integer sqlIndex;

    @ApiModelProperty(value = "语句")
    private String statement;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否启用")
    @NotNull(message = "是否启用不能为空", groups = {Save.class})
    private Boolean enabled;

    @ApiModelProperty(value = "创建人")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    private Integer updateUser;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "任务ID")
    @NotNull(message = "任务ID不能为空", groups = {Save.class})
    private Integer taskId;
}
