package com.test.flink.flinkdemo;

/**
 * Created by qinxy on 2020/3/23.
 */
public class ConfigKeys {

    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static final String SINK_DRIVER_URL = "jdbc:mysql://localhost:3306/test";
    public static final String SINK_USER = "root";
    public static final String SINK_SQL = "INSERT INTO flink_test (user_id,url,count,start_time,end_time) VALUES (?,?,?,?,?)";
    public static final String SINK_PASSWORD = "root";
}
