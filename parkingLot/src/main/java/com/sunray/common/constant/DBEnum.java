package com.sunray.common.constant;

public enum DBEnum {

    REDIS("redis"),
    MYSQL("mysql"),
    MONGODB("mongodb");

    public String value;

    DBEnum(String value) {
        this.value = value;
    }
}
