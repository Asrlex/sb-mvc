package dev.api.springmvc.security;

import dev.api.springmvc.security.guards.CompositeAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for setting up authentication and authorization.
 */
@Configuration
public class SecurityConfig {

	private final CompositeAuthenticationFilter compositeAuthenticationFilter;
	private final StandardAuthEntryPoint entryPoint;
	private final StandardAccessDeniedHandler deniedHandler;

	public SecurityConfig(CompositeAuthenticationFilter compositeAuthenticationFilter,
						  StandardAuthEntryPoint entryPoint,
						  StandardAccessDeniedHandler deniedHandler) {
		this.compositeAuthenticationFilter = compositeAuthenticationFilter;
		this.entryPoint = entryPoint;
		this.deniedHandler = deniedHandler;
	}

	/**
	 * Configures the security filter chain.
	 * Disables CSRF, sets up request authorization, exception handling, and adds custom authentication filter.
	 * @param http - the HttpSecurity object to configure
	 * @return the configured SecurityFilterChain
	 * @throws Exception - if an error occurs during configuration
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/**", "/healthcheck", "/v3/api-docs/**", "/api-docs/**", "/api-docs.yaml", "/swagger-api-docs", "/swagger-ui.html", "/swagger-ui/**").permitAll()
						.anyRequest().authenticated()
				)
				.exceptionHandling(ex -> ex
						.authenticationEntryPoint(entryPoint)
						.accessDeniedHandler(deniedHandler)
				)
				.addFilterBefore(compositeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	/**
	 * Exposes the AuthenticationManager bean.
	 * @param config - the AuthenticationConfiguration
	 * @return the AuthenticationManager
	 * @throws Exception - if an error occurs while retrieving the AuthenticationManager
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * Exposes the PasswordEncoder bean using BCrypt.
	 * @return the PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
