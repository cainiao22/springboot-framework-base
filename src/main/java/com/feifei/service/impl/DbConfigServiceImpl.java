package com.qding.bigdata.service.impl;

import com.qding.bigdata.mapper.DbConfigMapper;
import com.qding.bigdata.model.DbConfig;
import com.qding.bigdata.service.DbConfigService;
import com.qding.bigdata.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/10 17:57
 * @description
 */

@Service
@CacheConfig(cacheNames = "dbConfig")
public class DbConfigServiceImpl implements DbConfigService {

    @Autowired
    DbConfigMapper dbConfigMapper;

    @Override
    @Cacheable(key = "'name'.concat(':').concat(#name)", unless =  "#result == null")
    public DbConfig getDbConfigByName(String name) {
        return dbConfigMapper.selectByName(name);
    }

    @Override
    @Cacheable(key = "'id'.concat(':').concat(#id)", unless =  "#result == null")
    public DbConfig getDbConfigById(Long id) {
        return dbConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    @Cacheable(key = "'list'", unless =  "#result == null")
    public List<DbConfig> listAllDbConfigs() {
         return dbConfigMapper.listAllDbConfigs();
    }
}
