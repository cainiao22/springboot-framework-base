package com.qding.bigdata.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yanpf
 * @date 2019/6/18 15:51
 * @description
 */

@Data
@ApiModel("业务库的表信息")
public class DbTableSchemaVo {

    @ApiModelProperty(name = "表名称")
    private String name;
}
