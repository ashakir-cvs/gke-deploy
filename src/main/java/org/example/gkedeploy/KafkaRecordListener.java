package org.example.gkedeploy;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Slf4j
public class KafkaRecordListener {

    @Value("${username}")
    String username;

    @KafkaListener(topics = "topic_01")
    public void consumeKafkaMessage(String message) {
        log.warn(message);
        log.warn(username);
    }
}
