package com.qding.bigdata.utils;

import lombok.Data;

/**
 * @author yanpf
 * @date 2019/5/30 11:06
 * @description
 */

@Data
public class DriverConfig {

    private String userName;
    private String password;
    private String url;
    private String driverClass;

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + driverClass.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverConfig config = (DriverConfig) o;

        if (userName != null ? !userName.equals(config.userName) : config.userName != null) return false;
        if (password != null ? !password.equals(config.password) : config.password != null) return false;
        if (url != null ? !url.equals(config.url) : config.url != null) return false;
        return driverClass != null ? driverClass.equals(config.driverClass) : config.driverClass == null;
    }
}
