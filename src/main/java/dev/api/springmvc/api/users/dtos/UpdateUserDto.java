package dev.api.springmvc.api.users.dtos;

import dev.api.springmvc.common.entities.models.Users;

public record UpdateUserDto(Long id, String username, String email, String role) {

	public UpdateUserDto(Long id, String username, String email, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role.isBlank() ? "USER" : role;
	}

	public Users updateUser() {
		return new Users(this.id, this.username, this.email, this.role);
	}
}
