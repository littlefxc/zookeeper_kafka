package com.fengxuechao.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengxuechao
 * @date 2020/6/2
 */
@Slf4j
@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * Kafka 消息生产者发送消息
     *
     * @param input 消息内容
     */
    @GetMapping("/send/{input}")
    public void sendFoo(@PathVariable String input) {
        log.info("Kafka producer send message: {}", input);
        this.kafkaTemplate.send("test", input);
    }

    /**
     * Kafka 消息消费者消费消息
     *
     * @param message 消息内容
     */
    @KafkaListener(id = "group-0", topics = "test")
    public void listen(String message) {
        log.info("Kafka consumer receive message: {}", message);
    }
}
