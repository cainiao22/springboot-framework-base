package com.qding.bigdata.service.impl;

import com.qding.bigdata.annotations.HandlerType;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author yanpf
 * @date 2019/6/21 14:34
 * @description
 */

@Service
@HandlerType("DAY")
public class DailyPartitionStrategy extends DateFormatPartitionStrategy {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    DateFormat getDateFormat() {
        return dateFormat;
    }
}
