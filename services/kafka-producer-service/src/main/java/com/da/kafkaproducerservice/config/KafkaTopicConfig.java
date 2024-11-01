package com.da.kafkaproducerservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.name}")
    private String topic;

    @Value("${spring.kafka.topic.partitions}")
    private Integer partitions;

    @Bean
    public NewTopic topic(){
        return TopicBuilder
                .name(topic)
                .partitions(partitions)
                .build();
    }

}