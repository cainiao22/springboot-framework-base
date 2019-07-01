package com.qding.bigdata.po;

import com.qding.bigdata.annotations.validation.EnumValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author yanpf
 * @date 2019/6/12 10:57
 * @description
 */

@Data
@ApiModel(value = "DbSynConfigItemPo", description = "数据库同步配置项")
public class DbSynConfigItemPo {

    @ApiModelProperty(value = "数据库连接配置的主键", example = "1")
    @NotBlank(message = "目标数据库必填")
    private String destDbName;

    @ApiModelProperty(value = "数据库连接配置的主键", example = "1")
    @NotBlank(message = "目标表必填")
    private String destTableName;

    @ApiModelProperty(value = "同步方式(DATA | STRUCT)", example = "DATA")
    @EnumValue(strValues = {"DATA", "STRUCT"}, message = "同步类型必须为指定类型，{DATA, STRUCT}")
    private String syncType;

    @ApiModelProperty(value = "crontab表达式", example = "1")
    @NotBlank
    private String crontab;

    @ApiModelProperty(value = "执行次数", example = "1")
    @NotNull
    private Integer syncTimes;

    @ApiModelProperty(value = "表的注释", example = "用户信息表")
    private String comment;
}
