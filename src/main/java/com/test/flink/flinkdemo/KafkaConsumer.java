package com.test.flink.flinkdemo;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Created by qinxy on 2020/3/11.
 */
@Component
@Slf4j
public class KafkaConsumer {

    @Resource
    private DataProduce dataProduce;

//    @KafkaListener(topics = {Constants.topic})
//    public void receive(ConsumerRecord<String,String> record){
//        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
//        if(kafkaMessage.isPresent()){
//            String message = kafkaMessage.get();
//            log.info("receive record:{}" ,record);
//            log.info("receive message:{}",message);
//            DataEntity dataEntity = JSONObject.parseObject(message,DataEntity.class);
//        }
//    }
}
