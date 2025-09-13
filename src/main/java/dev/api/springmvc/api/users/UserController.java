package dev.api.springmvc.api.users;

import dev.api.springmvc.api.auth.entities.LoginRequest;
import dev.api.springmvc.api.users.dtos.CreateUserDto;
import dev.api.springmvc.api.users.dtos.UpdateUserDto;
import dev.api.springmvc.api.users.dtos.UserDto;
import dev.api.springmvc.common.entities.SearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping()
	@Operation(summary = "Get paginated Users", description = "Retrieve a list of paginated users")
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful retrieval of user list"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access")
	})
	public List<UserDto> getUsers(@RequestParam SearchCriteria searchCriteria) {
		return userService.list(searchCriteria);
	}

	@GetMapping("/all")
	@Operation(summary = "Get All Users", description = "Retrieve a list of all users")
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful retrieval of user list"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access")
	})
	public List<UserDto> getAllUsers() {
		return userService.listAll();
	}

	@GetMapping("/me")
	@Operation(summary = "Get Current User", description = "Retrieve details of the currently authenticated user")
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful retrieval of current user"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access")
	})
	public UserDto getCurrentUser(Authentication auth) {
		return userService.getCurrent(auth);
	}

	@GetMapping("/email/{email}")
	@Operation(summary = "Get User by Email", description = "Retrieve user details by email address")
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful retrieval of user by email"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
	})
	public UserDto getUserByEmail(@PathVariable String email) {
		return userService.findByEmail(email);
	}

	@GetMapping("/username/{username}")
	@Operation(summary = "Get User by Username", description = "Retrieve user details by username")
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful retrieval of user by username"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
	})
	public UserDto getUserByUsername(@PathVariable String username) {
		return userService.findByUsername(username);
	}

	@GetMapping("/id/{id}")
	@Operation(summary = "Get User by ID", description = "Retrieve user details by user ID")
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful retrieval of user by ID"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
	})
	public UserDto getUserById(@PathVariable String id) {
		return userService.findById(Integer.parseInt(id));
	}

	@PostMapping
	@Operation(summary = "Create User", description = "Create a new user")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
		description = "User creation payload",
		required = true
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created successfully"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access")
	})
	public UserDto createUser(@RequestBody CreateUserDto dto) {
		return userService.create(dto);
	}

	@PutMapping
	@Operation(summary = "Update User", description = "Update an existing user")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
		description = "User update payload",
		required = true
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated successfully"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
	})
	public UserDto updateUser(@RequestBody UpdateUserDto dto) {
		return userService.update(dto);
	}

	@PutMapping("/change-password")
	@Operation(summary = "Change Password", description = "Change the password of an authenticated user")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Change password payload",
			required = true
	)
	@ApiResponses(value = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password changed successfully"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden access")
	})
	public UserDto changePassword(LoginRequest dto) {
		return this.userService.changePassword(dto);
	}

}
