package com.qding.bigdata.service.impl;

import com.qding.bigdata.mapper.UserMapper;
import com.qding.bigdata.model.User;
import com.qding.bigdata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * @author yanpf
 * @date 2019/5/31 15:04
 * @description
 */
@CacheConfig(cacheNames = "users")
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    //@Cacheable(key = "#id", unless = "#result == null")
    public User getAllUsers(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
