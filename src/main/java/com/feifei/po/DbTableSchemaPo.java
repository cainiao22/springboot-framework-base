package com.qding.bigdata.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yanpf
 * @date 2019/6/18 18:29
 * @description
 */

@ApiModel("业务库表基本信息查询入参")
@Data
public class DbTableSchemaPo {

    @ApiModelProperty(name = "source配置名称")
    @NotBlank(message = "dbConfigName为空")
    String dbConfigName;

    @ApiModelProperty(name = "数据库名称")
    @NotBlank(message = "dbName为空")
    String dbName;

    @ApiModelProperty(name = "表名称")
    @NotBlank(message = "tableName为空")
    String tableName;
}
