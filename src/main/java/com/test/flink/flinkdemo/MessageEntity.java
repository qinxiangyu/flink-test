package com.test.flink.flinkdemo;

import lombok.Data;

import java.util.Date;

/**
 * Created by qinxy on 2020/3/11.
 */
@Data
public class MessageEntity {
    private String userId;
    private Date sendTime;
    private String visitUrl;
}
