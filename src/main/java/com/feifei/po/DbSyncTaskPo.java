package com.qding.bigdata.po;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yanpf
 * @date 2019/6/21 16:35
 * @description
 */

@Data
public class DbSyncTaskPo {

    private String partitionValue;

    @NotNull(message = "synConfigId不能为空")
    private Long synConfigId;
}
