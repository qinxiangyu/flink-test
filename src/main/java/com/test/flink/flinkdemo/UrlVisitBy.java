package com.test.flink.flinkdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by qinxy on 2020/3/23.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlVisitBy implements Serializable {

    private Long start;

    private Long end;

    private String url;

    private Long count;

    private String usrId;
}
