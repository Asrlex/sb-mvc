package dev.api.springmvc.api.users.dtos;

import dev.api.springmvc.common.entities.models.Users;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank String username, @NotBlank String email, @NotBlank String password, String role) {

	public CreateUserDto(String username, String email, String password, String role) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role.isBlank() ? "USER" : role;
	}

	public Users createUser() {
		return new Users(this.username, this.email, this.password, this.role);
	}
}
