package com.qding.bigdata.service;

import com.qding.bigdata.model.DbConfig;
import com.qding.bigdata.utils.Result;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/10 17:56
 * @description
 */
public interface DbConfigService {

    DbConfig getDbConfigByName(String name);

    DbConfig getDbConfigById(Long id);

    List<DbConfig> listAllDbConfigs();
}
