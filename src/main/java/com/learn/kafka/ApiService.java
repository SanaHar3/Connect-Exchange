package com.learn.kafka;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiService {

    private final WebClient webClient;

    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.exchangerate-api.com/v4/latest/USD").build();
    }

    public Mono<String> fetchData() {
        return webClient.get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
