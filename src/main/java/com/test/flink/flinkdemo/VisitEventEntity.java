package com.test.flink.flinkdemo;

import lombok.Data;

import java.util.Date;

/**
 * Created by qinxy on 2020/3/23.
 */
@Data
public class VisitEventEntity {

    private String visitUserId;

    private String visitUrl;

    private long visitTime;
}
