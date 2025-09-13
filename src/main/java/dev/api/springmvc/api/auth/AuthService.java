package dev.api.springmvc.api.auth;

import dev.api.springmvc.api.auth.entities.LoginRequest;
import dev.api.springmvc.api.auth.entities.RegisterRequest;
import dev.api.springmvc.api.users.UserRepository;
import dev.api.springmvc.common.entities.User;
import dev.api.springmvc.common.exceptions.ResourceAlreadyInUseException;
import dev.api.springmvc.common.exceptions.ResourceNotFoundException;
import dev.api.springmvc.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(JwtService jwtService,  UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.jwtService = jwtService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Authenticate user and return JWT token
	 * @param dto - login data
	 * @return JWT token
	 */
	public Map<String, String> login(LoginRequest dto) {
		User user = userRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + dto.getEmail()));

		if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
			throw new RuntimeException("Invalid credentials");
		}

		return mapUserClaimsToToken(user);
	}

	/**
	 * Register a new user and return JWT token
	 * @param dto - registration data
	 * @return JWT token
	 */
	public Map<String, String> register(RegisterRequest dto) {
		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new ResourceAlreadyInUseException("Email already in use", dto.getEmail());
		}

		User newUser = new User(
				dto.getUsername(),
				dto.getEmail(),
				passwordEncoder.encode(dto.getPassword()),
				dto.getRole());

		User savedUser = userRepository.save(newUser);

		return mapUserClaimsToToken(savedUser);
	}

	/**
	 * Map user claims to JWT token
	 * @param user - user entity
	 * @return map containing JWT token
	 */
	private Map<String, String> mapUserClaimsToToken(User user) {
		Map<String, Object> claims = Map.of(
				"id", user.getId(),
				"username", user.getUsername(),
				"role", user.getRole()
		);

		String token = jwtService.generateToken(user.getEmail(), claims);
		return Map.of("access_token", token);
	}
}
