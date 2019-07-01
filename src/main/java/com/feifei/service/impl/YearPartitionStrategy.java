package com.qding.bigdata.service.impl;

import com.qding.bigdata.annotations.HandlerType;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * @author yanpf
 * @date 2019/6/21 14:22
 * @description
 */

@Service
@HandlerType("YEAR")
public class YearPartitionStrategy extends DateFormatPartitionStrategy {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy");
    @Override
    DateFormat getDateFormat() {
        return dateFormat;
    }
}
