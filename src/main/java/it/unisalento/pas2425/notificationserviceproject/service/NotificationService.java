package it.unisalento.pas2425.notificationserviceproject.service;

import it.unisalento.pas2425.notificationserviceproject.dto.NotificationRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class NotificationService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://exp.host/--/api/v2/push/send")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public void sendPushNotification(NotificationRequestDTO request) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("to", request.getTo());
        payload.put("title", request.getTitle());
        payload.put("body", request.getBody());

        if (request.getData() != null) {
            payload.put("data", request.getData());
        }

        log.info("📦 Payload da inviare a Expo: {}", payload); // <--- AGGIUNTA IMPORTANTE

        webClient.post()
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("✅ Risposta da Expo: {}", response))
                .doOnError(error -> log.error("❌ Errore invio notifica:", error))
                .subscribe();
    }
}
