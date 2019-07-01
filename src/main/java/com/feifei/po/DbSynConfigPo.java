package com.qding.bigdata.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


/**
 * @author yanpf
 * @date 2019/6/11 18:12
 * @description
 */

@Data
@ApiModel(value = "DbSynConfigPo", description = "数据库同步配置信息")
public class DbSynConfigPo {

    @ApiModelProperty(value = "数据库连接配置的主键", example = "1")
    private Long dbConfigId;

    @ApiModelProperty(value = "源数据名称", example = "ds", required = true)
    @NotBlank(message = "源数据库必填")
    private String sourceDbName;

    @ApiModelProperty(value = "源数据库表", example = "user", required = true)
    @NotBlank(message = "源数据库表名必填")
    private String sourceTableName;

    @ApiModelProperty(value = "目标表的配置项", required = true)
    @Valid
    @Size(min = 1, message = "至少需要一个同步配置表")
    private List<DbSynConfigItemPo> synConfigItemPoList;
}
