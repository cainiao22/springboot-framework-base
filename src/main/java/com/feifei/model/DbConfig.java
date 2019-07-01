package com.qding.bigdata.model;

import lombok.Data;

@Data
public class DbConfig {
    private Long id;

    private String name;

    private String dbType;

    private String url;

    private String userName;

    private String password;

    private String comment;

    private Boolean autodestroy;
}