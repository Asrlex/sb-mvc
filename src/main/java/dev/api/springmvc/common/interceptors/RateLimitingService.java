package dev.api.springmvc.common.interceptors;

import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingService {

	private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

	public Bucket resolveBucket(String key) {
		return cache.computeIfAbsent(key, this::createNewBucket);
	}

	private Bucket createNewBucket(String key) {
		String role = key.contains(":") ? key.split(":")[1] : "ROLE_GUEST";
		return Bucket.builder()
				.addLimit(RateLimitingProfiles.getLimit(role))
				.build();
	}
}
