package com.qding.bigdata.controller;

import com.qding.bigdata.po.DbSynConfigPo;
import com.qding.bigdata.po.DbSyncTaskPo;
import com.qding.bigdata.service.HiveTableSchemaService;
import com.qding.bigdata.utils.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author yanpf
 * @date 2019/6/12 10:18
 * @description
 */

@Validated
@Slf4j
@Api(tags="数据同步配置相关操作")
@RestController("hiveTableSchema")
public class HiveTableSchemaController {


    @Autowired
    HiveTableSchemaService hiveTableSchemaService;


    @ApiOperation(value = "保存表的同步配置")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 400, message = "参数填写有问题"),
    })
    @PostMapping("saveSyncConfig")
    public Result<String> saveSyncConfig(@Validated @RequestBody @ApiParam(name="DbSynConfigPo", value = "同步表配置项", required = true) DbSynConfigPo po) {
        try {
            return hiveTableSchemaService.saveSyncConfig(po);
        } catch (SchedulerException e) {
            log.error("生成调度任务发生异常", e);
            return Result.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "立即触发任务")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 510, message = "服务器内部错误"),
    })
    @RequestMapping("triggerJob")
    public Result<String> triggerJob(@Validated DbSyncTaskPo taskPo){
        try {
            hiveTableSchemaService.triggerJob(taskPo);
        } catch (SchedulerException e) {
            log.error("", e);
            return Result.fail(HttpStatus.NOT_EXTENDED, e.getMessage());
        }

        return Result.success();
    }
}
