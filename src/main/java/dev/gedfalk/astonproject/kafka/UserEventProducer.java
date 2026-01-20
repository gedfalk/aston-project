package dev.gedfalk.astonproject.kafka;

import dev.gedfalk.astonproject.dto.UserEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, UserEventDto> kafkaTemplate;

    private static final String TOPIC = "userEvents";

    public void sendUserEvent(UserEventDto event) {
        log.info("Отправляем event: {}", event);

        CompletableFuture<SendResult<String, UserEventDto>> future =
                kafkaTemplate.send(TOPIC, event.getEmail(), event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Event отправлен успешно. Партиция {}, оффсет {}",
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Не удалось отправить event: {}", ex.getMessage());
            }
        });
    }
}
