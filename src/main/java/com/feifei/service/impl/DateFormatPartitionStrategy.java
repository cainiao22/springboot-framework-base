package com.qding.bigdata.service.impl;

import com.qding.bigdata.service.PartitionStrategy;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * @author yanpf
 * @date 2019/6/21 14:30
 * @description
 */
public abstract class  DateFormatPartitionStrategy  implements PartitionStrategy {

    abstract DateFormat getDateFormat();

    @Override
    public String paratitionValue() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        DateFormat dateFormat = getDateFormat();
        return dateFormat.format(calendar.getTime());
    }
}
