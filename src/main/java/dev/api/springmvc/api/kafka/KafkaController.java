package dev.api.springmvc.api.kafka;

import dev.api.springmvc.common.kafka.KafkaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/kafka")
public class KafkaController {

	private final KafkaService kafkaService;

	public KafkaController(KafkaService kafkaService) {
		this.kafkaService = kafkaService;
	}

	@PostMapping("/send")
	public void send(@RequestParam String topic, @RequestBody String kafkaMessage) {
		kafkaService.emit(topic, kafkaMessage);
	}
}
