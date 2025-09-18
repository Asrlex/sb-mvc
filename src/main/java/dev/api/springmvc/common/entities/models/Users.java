package dev.api.springmvc.common.entities.models;

import dev.api.springmvc.api.users.dtos.UserDto;
import dev.api.springmvc.common.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
public class Users extends AuditableEntity {

	@Setter
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Getter
	@Column(nullable = false)
	private String username;

	@Setter
	@Getter
	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password_hash;

	@Setter
	@Getter
	@Column(nullable = false)
	private String role;

	public Users(Long id, String username, String email, String password_hash, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password_hash = password_hash;
		this.role = role;
	}

	public Users(String username, String email, String password_hash, String role) {
		this.username = username;
		this.email = email;
		this.password_hash = password_hash;
		this.role = role;
	}

	public Users(Long id, String username, String email, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
	}

	public Users() {
		super();
	}

	public UserDto generateDto() {
		return new UserDto(this.id, this.username, this.email, this.role);
	}

	public String getPasswordHash() {
		return password_hash;
	}
	public void setPasswordHash(String password) {
		this.password_hash = password;
	}
}
