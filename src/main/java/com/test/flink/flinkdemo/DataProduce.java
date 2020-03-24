package com.test.flink.flinkdemo;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by qinxy on 2020/3/20.
 */
@Component
@Slf4j
public class DataProduce {

    public void produce() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        FlinkKafkaConsumer010<String> consumer = new FlinkKafkaConsumer010<String>(Constants.topic, new SimpleStringSchema(), properties);
        //从最早开始消费
        int[] arr = {0, 2};
        TypeInformation<Tuple3<String, VisitEventEntity, String>> typeInformation = TypeInformation.of(new TypeHint<Tuple3<String, VisitEventEntity, String>>() {
        });
        consumer.setStartFromEarliest();
        DataStream<String> stream = env.addSource(consumer);
        SingleOutputStreamOperator<UrlVisitBy> uvCounter = stream
                .map(str -> JSONObject.parseObject(str, VisitEventEntity.class))
                .map(visitEventEntity -> new Tuple3<>(visitEventEntity.getVisitUrl(), visitEventEntity, visitEventEntity.getVisitUserId()))
                .returns(typeInformation)
                .keyBy(arr)
                .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
                .allowedLateness(Time.minutes(1))
                .process(new ProcessWindowFunction<Tuple3<String, VisitEventEntity, String>, UrlVisitBy, Tuple, TimeWindow>() {
                    @Override
                    public void process(Tuple tuple, Context context, Iterable<Tuple3<String, VisitEventEntity, String>> elements, Collector<UrlVisitBy> out) throws Exception {
                        long count = 0;
                        Tuple2<String, String> tuple2 = null;
                        if (tuple instanceof Tuple2) {
                            tuple2 = (Tuple2<String, String>) tuple;
                        }
                        for (Tuple3<String, VisitEventEntity, String> element : elements) {
//                            log.info("element:{}",element);
                            count++;
                        }
                        TimeWindow timeWindow = context.window();
                        out.collect(new UrlVisitBy(timeWindow.getStart(), timeWindow.getEnd(), tuple2.f0, count, tuple2.f1));
                    }

                });
        //打印到控制台
        uvCounter.print();
        //写入数据库
        uvCounter.addSink(new JdbcWriter());
        //执行
        env.execute();
    }


}
