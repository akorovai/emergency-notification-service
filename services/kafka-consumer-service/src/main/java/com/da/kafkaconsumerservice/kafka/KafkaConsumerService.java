package com.da.kafkaconsumerservice.kafka;

import com.da.kafkaconsumerservice.dto.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class KafkaConsumerService {

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaManualAckListenerContainerFactory"
    )
    public void listen(@Payload EmailMessage emailMessage, Acknowledgment ack) {
        try {
            log.info("Consumed message: {}", emailMessage);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Processing failed for message: {}", emailMessage, e);
        }
    }
}