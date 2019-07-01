package com.qding.bigdata.controller;

import com.qding.bigdata.jobs.UserGetJob;
import com.qding.bigdata.model.User;
import com.qding.bigdata.service.UserService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author yanpf
 * @date 2019/5/31 16:10
 * @description
 */

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Scheduler scheduler;

    @Autowired
    Configuration freeMarkerCfg;

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public User getUser(){
        return userService.getAllUsers(1);
    }


    @GetMapping("buildUserGetJob")
    public String buildUserGetJob() throws SchedulerException {
        //设置开始时间为1分钟后
        long startAtTime = System.currentTimeMillis() + 1000 * 60;
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = UserGetJob.class.getName();
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(UserGetJob.class).withIdentity(name,group).build();
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(name,group).startAt(new Date(startAtTime))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(5)).build();
        //将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
        return "success";
    }

    @GetMapping("createFreeMarkerTemplate")
    public String createFreeMarkerTemplate() throws IOException, TemplateException {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        StringWriter writer = new StringWriter();
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("user", user);
        freeMarkerCfg.getTemplate("create_hive_table.ftl").process(dataModel, writer);

        return writer.toString();
    }
}
