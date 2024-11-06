package com.da.kafkaproducerservice.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage {

    private String id;
    private String subject;
    private String body;
    private String senderEmail;
    private List<String> recipientEmails;

}