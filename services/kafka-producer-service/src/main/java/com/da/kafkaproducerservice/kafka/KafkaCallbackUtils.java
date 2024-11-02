package com.da.kafkaproducerservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.support.SendResult;

import java.util.function.BiConsumer;

@Slf4j
public class KafkaCallbackUtils {
    public static <V> BiConsumer<SendResult<String, String>, Throwable> createKafkaCallback(String topic, String payload) {
        return (result, ex) -> {
            if (ex != null) {
                log.error("Failed to send message to topic: {}. Payload: {}", topic, payload, ex);
            } else {
                ProducerRecord<String, String> record = result.getProducerRecord();
                log.info("Producing request succeeded for topic: {}. Payload: {}", topic, record.value());
            }
        };
    }
}