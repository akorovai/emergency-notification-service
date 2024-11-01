package com.da.kafkaproducerservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        var res = kafkaProducerService.sendMessage(message);
        return ResponseEntity.ok().body(res);
    }


}
