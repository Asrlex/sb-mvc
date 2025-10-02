package dev.api.springmvc.common.kafka.events.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.api.springmvc.api.users.dtos.UserDto;
import dev.api.springmvc.api.users.dtos.UserEventDto;
import dev.api.springmvc.common.kafka.KafkaMessage;
import lombok.Getter;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Getter
@Component
public class UserEventSubscriber {

	private final Flux<UserEventDto> flux;

	/**
	 * Constructs a UserEventSubscriber that listens to user events from a Kafka topic.
	 *
	 * @param factory      the KafkaListenerContainerFactory used to create the listener container
	 * @param objectMapper the ObjectMapper used for deserializing Kafka messages
	 */
	public UserEventSubscriber(
			KafkaListenerContainerFactory<?> factory,
			ObjectMapper objectMapper) {

		this.flux = Flux.<UserEventDto>create(emitter -> {
			ConcurrentMessageListenerContainer<String, String> container =
					(ConcurrentMessageListenerContainer<String, String>) factory.createContainer("user-events-log");

			container.setupMessageListener((MessageListener<String, String>) record -> {
				try {
					KafkaMessage<UserDto> kafkaMessage =
							objectMapper.readValue(record.value(),
									new TypeReference<>() {});
					emitter.next(new UserEventDto(kafkaMessage.getType(), kafkaMessage.getPayload()));
				} catch (Exception e) {
					emitter.error(e);
				}
			});

			container.start();

			emitter.onDispose(container::stop);
		}).share();
	}
}

