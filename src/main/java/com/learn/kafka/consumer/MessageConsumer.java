package com.learn.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.kafka.model.ExchangeModel;
import com.learn.kafka.repository.ExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.math.BigDecimal;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@Component
@Slf4j
public class MessageConsumer {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @KafkaListener(topics = "mon-tunnel-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        log.info("Message receive : {}", message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(message, Map.class);

            ExchangeModel doc = new ExchangeModel();
            doc.setId(UUID.randomUUID().toString());
            doc.setBase((String) map.get("base"));
            doc.setDate((String) map.get("date"));
            doc.setRates((Map<String, Double>) map.get("rates"));

            // Conversion du time_last_updated en ISO8601
            Object timeLastUpdatedObj = map.get("time_last_updated");
            if (timeLastUpdatedObj != null) {
                long epochSeconds = Long.parseLong(timeLastUpdatedObj.toString());
                String isoDate = Instant.ofEpochSecond(epochSeconds).toString();
                doc.setTimestamp(isoDate);
            } else {
                doc.setTimestamp(Instant.now().toString());
            }

            exchangeRepository.save(doc);

            System.out.println("OK dans Elasticsearch");

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

}