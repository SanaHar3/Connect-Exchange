package com.learn.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.kafka.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RealTimePublisher {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private ObjectMapper objectMapper;

    // Publie les taux toutes les 5 minutes (300000 ms). Change la devise si tu veux.
    @Scheduled(fixedRate = 300000)
    public void fetchAndPublish() {
        try {
            var data = exchangeService.getExchangeRates("USD");
            String jsonMessage = objectMapper.writeValueAsString(data);
            messageProducer.sendMessage("mon-tunnel-topic", jsonMessage);
            System.out.println("Taux publiés automatiquement sur Kafka !");
        } catch (JsonProcessingException e) {
            System.err.println("Erreur de sérialisation JSON : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération ou l'envoi des taux : " + e.getMessage());
        }
    }
}
