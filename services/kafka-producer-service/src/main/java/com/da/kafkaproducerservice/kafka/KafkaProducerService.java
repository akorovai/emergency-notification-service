package com.da.kafkaproducerservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaProducerService {

    @Value("${spring.kafka.topic.name}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessage(String message) {
        kafkaTemplate.send(topic, message);
        return "Success";
    }
}
