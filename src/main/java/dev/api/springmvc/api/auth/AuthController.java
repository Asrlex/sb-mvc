package dev.api.springmvc.api.auth;

import dev.api.springmvc.api.auth.entities.LoginRequest;
import dev.api.springmvc.api.auth.entities.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	@Operation(summary = "User Login", description = "Authenticate a user and return a JWT token")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
		description = "Login payload",
		required = true
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful login"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Invalid credentials"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access")
	})
	public Map<String, String> login(@RequestBody LoginRequest dto) {
		return this.authService.login(dto);
	}

	@PostMapping("/register")
	@Operation(summary = "User Registration", description = "Register a new user")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
		description = "Registration payload",
		required = true
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Successful registration"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access")
	})
	public Map<String, String> register(@RequestBody RegisterRequest dto) {
		return this.authService.register(dto);
	}
}

