package com.test.flink.flinkdemo;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by qinxy on 2020/3/11.
 */
@Component
public class DataMaker {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    @Resource
    KafkaProducer kafkaProducer;
    String[] urls = new String[]{"url123","url456","url789"};
    String[] userIds = new String[]{"1111","222","333","444","555","666"};


    public void makeData() throws InterruptedException {
        long id = 1;

        while (true){
            kafkaProducer.send(urls[RandomUtils.nextInt(0,3)], userIds[RandomUtils.nextInt(0,6)]);
            Thread.sleep(1000);
        }

    }

}
