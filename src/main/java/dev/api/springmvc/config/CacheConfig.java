package dev.api.springmvc.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
	@Bean
	@Profile("dev")
	public Caffeine<Object, Object> caffeineConfig() {
		return Caffeine.newBuilder()
				.expireAfterWrite(5, TimeUnit.MINUTES)
				.maximumSize(100);
	}
}
