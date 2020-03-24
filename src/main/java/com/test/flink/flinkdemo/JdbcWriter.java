package com.test.flink.flinkdemo;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by qinxy on 2020/3/23.
 */
public class JdbcWriter extends RichSinkFunction<UrlVisitBy> {
    private Connection connection;
    private PreparedStatement preparedStatement;
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        // 加载JDBC驱动
        Class.forName(ConfigKeys.DRIVER_CLASS);
        // 获取数据库连接
        connection = DriverManager.getConnection(ConfigKeys.SINK_DRIVER_URL,ConfigKeys.SINK_USER,ConfigKeys.SINK_PASSWORD);//写入mysql数据库
        preparedStatement = connection.prepareStatement(ConfigKeys.SINK_SQL);//insert sql在配置文件中
        super.open(parameters);
    }

    @Override
    public void close() throws Exception {
        super.close();
        if(preparedStatement != null){
            preparedStatement.close();
        }
        if(connection != null){
            connection.close();
        }
        super.close();
    }

    @Override
    public void invoke(UrlVisitBy urlVisitBy, Context context) throws Exception {
        try {
            preparedStatement.setString(1,urlVisitBy.getUrl());
            preparedStatement.setString(2,urlVisitBy.getUsrId());
            preparedStatement.setLong(3,urlVisitBy.getCount());
            preparedStatement.setLong(4,urlVisitBy.getStart());
            preparedStatement.setLong(5,urlVisitBy.getEnd());

            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
