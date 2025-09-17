package dev.api.springmvc.common.interceptors;

import io.github.bucket4j.Bandwidth;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;

public class RateLimitingProfiles {
	@Value("${ratelimit.admin}")
	private static Integer ADMIN;
	@Value("${ratelimit.admin}")
	private static Integer USER;
	@Value("${ratelimit.admin}")
	private static Integer API;
	@Value("${ratelimit.admin}")
	private static Integer GUEST;

	public static Bandwidth getLimit(String role) {
		return Bandwidth.simple(
				switch (role) {
			case "ROLE_ADMIN" -> ADMIN;
			case "ROLE_USER" -> USER;
			case "ROLE_API" -> API;
			default -> GUEST;
		}, Duration.ofMinutes(1));
	}
}
