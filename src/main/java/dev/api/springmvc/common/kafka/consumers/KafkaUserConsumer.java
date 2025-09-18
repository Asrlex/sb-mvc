package dev.api.springmvc.common.kafka.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.api.springmvc.common.entities.models.Users;
import dev.api.springmvc.common.kafka.KafkaMessage;
import dev.api.springmvc.common.kafka.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for handling user-related events.
 */
@Slf4j(topic = "KafkaUserConsumer")
@Component
public class KafkaUserConsumer extends KafkaGenericConsumer<Users> {

	private static final String TOPIC = "user-events";
	private KafkaService kafkaService;

	public KafkaUserConsumer(ObjectMapper mapper, KafkaService kafkaService) {
		super(mapper, kafkaService);
	}

	/**
	 * Listens to the "user-events" topic and processes incoming messages.
	 *
	 * @param messageJson the JSON message received from Kafka
	 */
	@KafkaListener(topics = TOPIC, groupId = "springmvc-group")
	public void consume(String messageJson) {
		super.consume(messageJson, new TypeReference<KafkaMessage<Users>>() {});
	}

	/**
	 * Handles the user event based on its type.
	 *
	 * @param message the Kafka message containing the user event
	 */
	@Override
	protected void handleEvent(KafkaMessage<Users> message) {
		switch (message.getType()) {
			case CREATE -> log.info("Handle user creation logic: {}", message.getPayload());
			case UPDATE -> log.info("Handle user update logic: {}", message.getPayload());
			case DELETE -> log.info("Handle user deletion logic: {}", message.getPayload());
			case RESTORE -> log.info("Handle user restoration logic: {}", message.getPayload());
			default -> log.warn("Unknown event type: {}", message.getType());
		}
	}
}
