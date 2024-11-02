package com.da.kafkaproducerservice.kafka;

import com.da.kafkaproducerservice.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendMailMessage(@RequestBody EmailMessage message) {
        var res = kafkaProducerService.sendMailMessage(message);
        return ResponseEntity.ok().body(res);
    }


}
