package org.example.gkedeploy;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class KafkaRecordListener {

    @KafkaListener(topics = "test_11")
    public void consumeKafkaMessage(String message) {
        log.warn(message);
    }
}
