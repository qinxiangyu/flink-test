package com.test.flink.flinkdemo;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by qinxy on 2020/3/11.
 */
@Component
@Slf4j
public class KafkaProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String url,String userId) {
        VisitEventEntity message = new VisitEventEntity();
        message.setVisitUrl(url);
        message.setVisitUserId(userId);
        message.setVisitTime(System.currentTimeMillis());
//        log.info("send:{}",JSONObject.toJSONString(message));
        kafkaTemplate.send(Constants.topic, JSONObject.toJSONString(message));
    }
}
