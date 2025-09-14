package dev.api.springmvc.api.users.dtos;

import dev.api.springmvc.common.entities.models.User;

public record UpdateUserDto(Integer id, String username, String email, String role) {

	public UpdateUserDto(Integer id, String username, String email, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role.isBlank() ? "USER" : role;
	}

	public User updateUser() {
		return new User(this.id, this.username, this.email, this.role);
	}
}
