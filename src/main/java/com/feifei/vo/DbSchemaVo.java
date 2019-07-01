package com.qding.bigdata.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/18 15:50
 * @description
 */

@Data
@ApiModel("业务方数据库")
public class DbSchemaVo {

    @ApiModelProperty(name = "数据库名称")
    private String dbName;

    @ApiModelProperty(name = "数据库下的所有表")
    private List<DbTableSchemaVo> tableSchemaVoList;
}
