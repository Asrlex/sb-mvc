package dev.api.springmvc.config;

import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * Logs all registered endpoints at application startup.
 */
@Component
public class EndpointLogger implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(EndpointLogger.class);

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	/**
	 * Logs all registered endpoints when the application context is refreshed.
	 * Logs HTTP methods, URL patterns, and method parameters.
	 * @param event - the context refreshed event
	 */
	@Override
	public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
		log.info("======== Registered Endpoints ========");
		List<Map.Entry<RequestMappingInfo, HandlerMethod>> endpoints =
				new ArrayList<>(handlerMapping.getHandlerMethods().entrySet().stream().filter(e ->
						!e.getKey().getActivePatternsCondition().toString().contains("/error")
				).toList());
		endpoints.sort(
				Comparator.comparing(e ->
						e.getValue().getMethod().getDeclaringClass().getSimpleName() + "." + e.getValue().getMethod().getName())
		);
		endpoints.forEach(
				e -> logEndpoint(e.getKey(), e.getValue()));
	}

	/**
	 * Logs a single endpoint's details.
	 * @param mapping - the request mapping info
	 * @param method - the handler method
	 */
	private void logEndpoint(RequestMappingInfo mapping, HandlerMethod method) {
		String methods = mapping.getMethodsCondition().isEmpty()
				? "[ALL]"
				: mapping.getMethodsCondition().toString();

		String patterns = mapping.getActivePatternsCondition()
				.toString()
				.replace("[", "")
				.replace("]", "");

		String methodParams = method.getMethod().getParameters().length == 0
				? ""
				: "(" + Arrays.stream(method.getMethod().getParameters())
				.map(p -> p.getType().getSimpleName() + " " + p.getName())
				.reduce((a, b) -> a + ", " + b).orElse("") + ")";

		log.info(String.format("%-10s %-30s %-40s", methods, patterns, methodParams));
	}
}
