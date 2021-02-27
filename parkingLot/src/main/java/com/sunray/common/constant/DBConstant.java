package com.sunray.common.constant;

public class DBConstant {

    public static DBEnum DB_TYPE = DBEnum.REDIS;


    // ==============================Redis================================
    public final static String REDIS_HOST = "127.0.0.1";
    public final static String REDIS_PASSWORD = "123456";
    public final static int REDIS_PORT = 6380;
    public final static int REDIS_DB = 0;

    public final static int REDIS_MAX_TOTAL = 1000;
    public final static int REDIS_MAX_IDLE = 20;
    public final static int REDIS_MAX_WAIT_MILLIS = 6000;


    // ==============================Mysql================================
    public final static String MYSQL_DRIVER_CLASS = "org.sqlite.JDBC";
    public final static String MYSQL_CONNECT_URL = "jdbc:sqlite:./bin/park.db";

}
