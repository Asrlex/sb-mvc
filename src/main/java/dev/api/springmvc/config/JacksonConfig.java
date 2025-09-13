package dev.api.springmvc.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

	/**
	 * Customize Jackson to serialize dates as ISO-8601 strings instead of timestamps.
	 * @return the customizer
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> builder
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}
}
