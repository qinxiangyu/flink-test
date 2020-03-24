package com.test.flink.flinkdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by qinxy on 2020/3/11.
 */
@RestController
public class TestController {

    @Resource
    DataMaker dataMaker;

    @Resource
    DataProduce dataProduce;

    @GetMapping("test")
    public String test() throws InterruptedException {
        //造数据
        dataMaker.makeData();
        return "success";
    }

    @GetMapping("test-produce")
    public String produce() throws Exception {
        //造数据
        dataProduce.produce();
        return "success";
    }
}
