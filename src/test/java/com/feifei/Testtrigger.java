package com.qding.bigdata;

import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.calendar.HolidayCalendar;

import java.util.Date;

/**
 * @author yanpf
 * @date 2019/5/31 11:33
 * @description
 */
public class Testtrigger {

    public static void main(String[] args) {
        HolidayCalendar holidayCalendar = new HolidayCalendar();
        holidayCalendar.addExcludedDate(new Date());

    }
}
