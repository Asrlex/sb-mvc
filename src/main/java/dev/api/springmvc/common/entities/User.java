package dev.api.springmvc.common.entities;

import dev.api.springmvc.api.users.dtos.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password_hash;

	@Column(nullable = false)
	private String role;

	public User(int id, String username, String email, String password_hash, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password_hash = password_hash;
		this.role = role;
	}

	public User(String username, String email, String password_hash, String role) {
		this.username = username;
		this.email = email;
		this.password_hash = password_hash;
		this.role = role;
	}

	public User(@NotBlank Integer id, @NotBlank String username, @NotBlank String email, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
	}

	public User() {
		super();
	}

	public UserDto generateDto() {
		return new UserDto(this.id, this.username, this.email, this.role);
	}

	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String name) {
		this.username = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return password_hash;
	}
	public void setPasswordHash(String password) {
		this.password_hash = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
